package cc.sharesdk;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-25
 * Time: 16:29
 * Version 1.0
 */

public class FakeChildActivity extends FakeActivity {

    protected  AuthorizeHelper authorizeHelper;

    public FakeChildActivity(){

    }

    public void setAuthorizeHelper(AuthorizeHelper authorizeHelper){
        this.authorizeHelper = authorizeHelper;
        super.show(authorizeHelper.getPlatform().getContext(), null);
    }

    public AuthorizeHelper getAuthorizeHelper(){
        return this.authorizeHelper;
    }

}
