package com.springmvc.controller;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gavin on 2018/3/8.
 */
@Controller
public class UserInfoController {

//    @Autowired
//    private UserService userService;
    @RequestMapping("/userInfo")
    public HttpEntity userInfo(HttpServletRequest request)throws OAuthSystemException{
        System.out.println("-----------------服务端/userInfo------");

        try {
            //获取客户端传来的OAuth资源请求
            OAuthAccessResourceRequest oAuthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            //获取Access Token
            String accessToken = oAuthRequest.getAccessToken();
            System.out.println("accessToken:" + accessToken);
            //验证Access Token
            if(accessToken == null || accessToken == ""){
                //如果不存在/过期了，返回未知验证错误，需重新验证
                OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED).setError(OAuthError.ResourceResponse.INVALID_TOKEN).buildHeaderMessage();

                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,oAuthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }
            //返回用户名
//            User user = userService.selectByPrimaryKey(1);
            String user = "hyperflex";
            String username = accessToken + "----" + Math.random() + "------" + user;
            System.out.println(username);
            return  new ResponseEntity(username, HttpStatus.OK);
        } catch (OAuthProblemException e) {
            e.printStackTrace();
            //检查是否设置了错误码
            String errorCode = e.getError();
            if(OAuthUtils.isEmpty(errorCode)){
                OAuthResponse oAuthASResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED).buildHeaderMessage();

                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,oAuthASResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity(headers,HttpStatus.UNAUTHORIZED);
            }
            OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED).setError(e.getError()).setErrorDescription(e.getDescription()).setErrorUri(e.getUri()).buildHeaderMessage();

            HttpHeaders headers = new HttpHeaders();
            headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            System.out.println("-----------服务端/userInfo----------");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
