package com.thinkgem.jeesite.common.OAuth;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by Gavin on 2018/3/23.
 */
@RequestMapping("/server")
@Controller
public class AuthorizeController {


    //返回code  响应客户端第一步
    @RequestMapping("/responseCode")
    public Object toShowUser(Model model, HttpServletRequest request){
//        System.out.println(request.toString());
        try {
            //构建OAuth授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            System.out.println(oauthRequest.getClientId().toString());
            if(oauthRequest.getClientId() != null && oauthRequest.getClientId() != ""){
                //利用oauth授权请求设置responseType，目前仅支持CODE，另外还有TOKEN
                String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
                //进行OAuth响应构建
                OAuthASResponse.OAuthAuthorizationResponseBuilder builder=
                        OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
                //设置授权码
                builder.setCode("authorizationCode");
                //得到到客户端重定向地址
                String redirectURI =oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
                //构建响应
                final OAuthResponse response =builder.location(redirectURI).buildQueryMessage();
                System.out.println("服务端/responseCode内，返回的回调路径："+response.getLocationUri());


                String responseUri =response.getLocationUri();
                //根据OAuthResponse返回ResponseEntity响应
                HttpHeaders headers =new HttpHeaders();

                try {
                    headers.setLocation(new URI(redirectURI));
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
                return "redirect:" + responseUri;
            }

        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }
        return null;
    }



    //获取客户端的code码，向客户端返回access token
    @RequestMapping(value="/responseAccessToken",method = RequestMethod.POST)
    public HttpEntity token(HttpServletRequest request){
        System.out.println("--------服务端/responseAccessToken-------------------------");
        OAuthIssuer oauthIssuerImpl=null;
        OAuthResponse response=null;
    //构建OAuth请求
        try {
            OAuthTokenRequest oauthTokenRequest = new OAuthTokenRequest(request);
            String authCode = oauthTokenRequest.getParam(OAuth.OAUTH_CODE);
            String clientSecret = oauthTokenRequest.getClientSecret();
            if(clientSecret!=null||clientSecret!=""){
                //生成Access Token
                oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                final String accessToken = oauthIssuerImpl.accessToken();
                System.out.println("accessToken:" + accessToken);
                //生成OAuth响应
                response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                        .setAccessToken(accessToken)
                        .buildJSONMessage();
                //根据OAuthResponse生成ResponseEntity
                return  new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/userInfo")
    public HttpEntity userInfo(HttpServletRequest request)throws OAuthSystemException{
        System.out.println("-----------服务端/userInfo------------------");


        //获取客户端传来的OAuth资源请求
        try {
            OAuthAccessResourceRequest oauthRequest =new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            //获取Access Token
            String accessToken =oauthRequest.getAccessToken();
            System.out.println("accessToken");

            //验证Access Token

            if (accessToken==null||accessToken=="") {
              // 如果不存在/过期了，返回未验证错误，需重新验证
            OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                    .buildHeaderMessage();
              HttpHeaders headers = new HttpHeaders();
              headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,
                oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }
            String username = accessToken + "---" + Math.random() + "---   hyperflex";

        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }
        return null;
    }
}

