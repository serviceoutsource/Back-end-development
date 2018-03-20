package com.springmvc.controller;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gavin on 2018/3/7.
 */
@Controller
public class AccessTokenController {

    //获取客户端的code码，向客户端返回access token
    @RequestMapping(value = "/responseAccessToken", method = RequestMethod.POST)
    public HttpEntity token(HttpServletRequest request){
        System.out.println("服务端/responseAccessToken");
        OAuthIssuer oAuthIssuerImpl = null;
        OAuthResponse response = null;
        //构建OAuth请求
        try {
            OAuthTokenRequest oAuthRequest = new OAuthTokenRequest(request);
            String authCode = oAuthRequest.getParam(OAuth.OAUTH_CODE);
            String clientSecret = oAuthRequest.getClientSecret();
            if(clientSecret != null || clientSecret != ""){
                //生成Access Token
                oAuthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                final String accessToken = oAuthIssuerImpl.accessToken();
                System.out.println(accessToken);
                //生成OAuth响应
                response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken).buildJSONMessage();
            }
            System.out.println("----------服务端/responseAccessToken--------------");
            //根据OAuthResponse生成ResponseEntity
            return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }
        return null;
    }
}
