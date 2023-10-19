package com.gn.demo.config.security.constant;/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/8/2 14:02
 */

import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static final String ROLE_LOGIN =  "role_login";
    public static final String LOCALHOST = "127.0.0.1";
    /**
     * 加密相关
     */
    public static String SALT = "guining";
    public static final int HASH_ITERATIONS = 1;

    public static final String REQUEST_HEADER = "X-Token";
    public static final String ADMIN_ACCESS_TOKEN = "admin:token_";

    public static Map<String, String> URL_MAPPING_MAP = new HashMap<>();

    public static final String JWT_USER_NAME = "jwt-user-name-key";

    public static final String JWT_PERMISSIONS_KEY = "jwt-permissions-key_";

    public static final String JWT_ROLES_KEY = "jwt-roles-key_";

    public static final String JWT_REFRESH_TOKEN_BLACKLIST = "jwt-refresh-token-blacklist_";

    public static final String JWT_ACCESS_TOKEN_BLACKLIST = "jwt-access-token-blacklist_";

    public static final String ACCESS_TOKEN = "X-Token";

    public static final String GN_ACCESS_TOKEN = "gn:token_";

    public static final String JWT_USER = "JWT_USER_";

    public static final String JWT_ROLE_LIST = "jwt_role_list_";

    public static final String JWT_SecurityUser = "jwt_SecurityUser_";

    public static final String JWT_authorities = "jwt_authorities_";

    public static final String REFRESH_TOKEN = "refresh_token";

    public static final String ACCOUNT_LOCK_KEY = "gn:account-lock-key-";

    public static final String DELETED_USER_KEY = "gn:deleted-user-key_";

    /**
     * 请求头类型
     * application/x-www-form-urlencoded: form表单格式
     * application/json: json格式
     */
    public static final String REQUEST_HEADERS_CONTENT_TYPE = "application/json";
}
