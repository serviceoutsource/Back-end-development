package com.springmvc.controller;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Gavin on 2018/3/7.
 */
@Controller
public class AuthorizeController {
    private String ClientId;
    private String ClientSecret;
    //向客户端返回授权许可码code
    @RequestMapping("/responseCode")
    public void ResponseCode(Model model, HttpServletRequest request){
        try {
            //获取Oauth2.0参数
            OAuthAuthzRequest oauthzRequest = new OAuthAuthzRequest(request);
            ClientId = oauthzRequest.getClientId();
            ClientSecret = oauthzRequest.getClientSecret();
            System.out.println("ClientId:" + ClientId);
            System.out.println("Clientsecret:" + ClientSecret);
            System.out.println("ResponseType:" + oauthzRequest.getResponseType());
            System.out.println("RedirectURL:" + oauthzRequest.getRedirectURI());
            //比对用户信息
            if(ClientId.equals("hyperflex") && ClientSecret.equals("hyperflex")){
                //设置授权码
                String authorizationCode = "authorizationCode";
                //利用oauth授权请求设置responseType（支持code和token）
                String responseType = oauthzRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
                //进行OAuth响应构建
                OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
                //设置授权码
                builder.setCode(authorizationCode);
                //得到的客户端重定向地址
                String redirectURL = oauthzRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
                //构建响应
                final OAuthASResponse response = (OAuthASResponse) builder.location(redirectURL).buildQueryMessage();
                System.out.println("服务器端/responseCode内，返回的回调路径：" + response.getLocationUri());

                String responseUrl = response.getLocationUri();

                //根据OAuthResponse返回ResponseEntity回应
                HttpHeaders headers = new HttpHeaders();
                try {
                    headers.setLocation(new URI(response.getLocationUri()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }
    }
}
