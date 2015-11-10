package cc.sharesdk;

/**
 * Created by abc on 15/9/24.
 */
public interface AuthorizeHelper {

    Platform getPlatform();

    String getAuthorizeUrl();

    String getRedirectUri();

}
