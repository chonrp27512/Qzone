package cc.sharesdk;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-24
 * Time: 11:37
 * Version 1.0
 */

public abstract class AuthorizeHelperUtil implements AuthorizeHelper{

    private Platform platform;

    public AuthorizeHelperUtil(Platform platform){
        this.platform = platform;
    }

    @Override
    public Platform getPlatform() {
        return platform;
    }

    public abstract void getToken(String code);

    public abstract void userInfo(String uid);
}
