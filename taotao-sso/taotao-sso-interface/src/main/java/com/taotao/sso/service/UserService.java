package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * 〈用户登陆〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/11
 */
public interface UserService {

    TaotaoResult checkData(String data, int type);

    TaotaoResult register(TbUser user);

    TaotaoResult login(String username, String password);

    TaotaoResult getUserByToken(String token);

    TaotaoResult logout(String token);
}
