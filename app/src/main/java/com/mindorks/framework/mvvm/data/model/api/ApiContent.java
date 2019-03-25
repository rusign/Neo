package com.mindorks.framework.mvvm.data.model.api;

import java.util.List;

public class ApiContent {

    public class CircleListContent{
        public int id;
        public String created;
        public String updated;
        public String name;
        public ConversationLinks link;
    }

    //--------------------------Settings-------------------------------//



    //--------------------------Conversation-------------------------------//

    public class MessageContent{
        public int id;
        public String content;
        public int circle_id;
        public boolean sent;
        public boolean read;
        public MessageLinks link;
        public List<MessageMedias> medias;
    }

    public class MessageMedias{
        public int id;
        public String upload_time;
        public MessageMessage message;
        public MessageMedia media;

    }

    public class MessageMessage{
        public int id;
        public int link_id;
        public String sent;
        public String read;
        public String content;
        public int medias;
    }

    public class MessageMedia{
        public int id;
        public String filename;
        public String extension;
        public String identifier;
        public String uploaded;
    }


    public class MessageLinks{
        public int circle_id;
        public String created;
        public int id;
        public String privilege;
        public String updated;
        public int conversation_id;
        public int user_id;
    }

    public class ConversationListContent{
        public int id;
        public String name;
        public String created;
        public String updated;
        public int circle_id;
        public boolean device_access;
    }

    public class ConversationCircle{
        public String created;
        public int device;
        public int id;
        public String name;
        public String updated;
    }

    public class ConversationConvId{
        public int circle_id;
        public String created;
        public Boolean device_access;
        public int id;
        public String name;
        public String updated;
    }
    public class ConversationMessage{
        public String content;
        public int id;
        public int link_id;
        public int medias;
        public String read;
        public String sent;
    }

    public class ConversationUserId{
        public String birthday;
        public String created;
        public String email;
        public String first_name;
        public int id;
        public Boolean isOnline;
        public String last_name;
        public String type;
        public String updated;
    }

    public class ConversationLinks{
        public int circle_id;
        public String created;
        public int id;
        public String privilege;
        public String updated;
        public ConversationConvId conversation_id;
        public List<ConversationMessage> messages;
        public ConversationUserId user_id;
    }

    public class ConversationInfoContent{
        public String name;
        public String updated;
        public String created;
        public Boolean device_access;
        public int id;
        public ApiContent.ConversationCircle circle;
        public List<ApiContent.ConversationLinks> links;
        public List<ApiContent.ConversationMessage> messages;
    }

    public class ConversationCreateContent {
        public int conversation_id;
        public String created;
        public Boolean device_access;
        public int id;
        public String name;
        public String updated;
    }

    //--------------------------UserInfo-------------------------------//

    public class UserCirles{
        public ConversationCircle circle;
        public String created;
        public int id;
        public String privilege;
        public String updated;
        public int user;
    }

    public class UserCirleInvite{
        public String created;
        public int id;
        public String updated;
        public String name;
    }

    public class UserInvites{
        public int id;
        public String updated;
        public String created;
        public int user;
        public UserCirleInvite circle;
    }

    public class UserConv{
        public int circle_id;
        public int conversation_id;
        public int id;
        public String created;
        public String privilege;
        public String updated;
        public int user_id;
    }


    public class UserMediasUser{
        public int id;
        public String email;
        public String first_name;
        public String last_name;
        public String birthday;
        public String created;
        public String updated;
        public Boolean isOnline;
        public String type;
    }

    public class UserMediasMedia{
        public int id;
        public String filename;
        public String extension;
        public String identifier;
        public String uploaded;
    }

    public class UserMedias{
        public int id;
        public UserMediasUser user;
        public UserMediasMedia media;
        public String upload_time;
        public String purpose;
    }

    public class UserInfo {
        public String birthday;
        public List<UserCirles> circles;
        public List<UserConv> conversations;
        public String created;
        public String email;
        public Boolean facebook;
        public String first_name;
        public Boolean hangout;
        public int id;
        public List<UserInvites> invites;
        public Boolean isOnline;
        public String last_name;
        public List<UserMedias> medias;
        public String type;
        public String updated;
    }


    //--------------------------CircleInfo-------------------------------//

    public class CircleUserUser{
        public int id;
        public String email;
        public String first_name;
        public String last_name;
        public String birthday;
        public String created;
        public String updated;
        public Boolean isOnline;
        public String type;
    }

    public class CircleUser{
        public int circle;
        public int id;
        public String created;
        public String privilege;
        public String updated;
        public CircleUserUser user;
    }

    public class CircleDevice {
        public Boolean activated;
        public int circle_id;
        public String created;
        public int id;
        public Boolean is_online;
        public String name;
        public String updated;
        public String username;
    }

    public class ClircleCirleInvite{
        public String created;
        public int id;
        public String updated;
        public int user_id;
        public int circle_id;
    }

    public class ClircleInvites{
        public int id;
        public String updated;
        public UserMediasUser user;
        public int circle;
    }

    public class ClircleContactsInfo{
        public List<CircleUser> users;
        public int id;
        public String name;
        public String updated;
        public List<ConversationConvId> conversations;
        public String created;
        public CircleDevice device;
        public List<ClircleInvites> invites;
        public List<UserMedias> medias;
    }
}
