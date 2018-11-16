package com.yunzhou.tdinformation.bean.blog;

import com.google.gson.annotations.SerializedName;
import com.yunzhou.common.http.bean.base.Entity;
import com.yunzhou.tdinformation.bean.community.Blog;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.bean.blog
 *  @文件名:   PostEntity
 *  @创建者:   lz
 *  @创建时间:  2018/10/11 19:29
 *  @描述：
 */

public class PostEntity extends Entity {


    /**
     * postDatial : {"postId":48,"content":"121212","createTime":"2018-10-08 20:33:49","author":"yunzhou_u16or5","status":1,"circleType":1,"uid":47,"list":[{"smallImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvUt2AG7Q8AAAQZby4Qcw663.jpg","originalImg":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvUt2AKZl3AAA-o0dRv6U820.jpg"}],"authorNickName":"小熊猫","authorAvatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFu-1XmATdhGAATNn5QntKU569.jpg@@http://39.108.61.68:80/group1/M00/00/08/rBKZOFu-1XmAEn18AAAW-_Wnt-0996.jpg","isCheck":0,"isLike":0,"isCollect":0,"collectNum":2,"commitNum":20,"likeNum":2}
     * commits : {"isLastPage":false,"list":[{"commentsId":457,"uid":48,"content":"Asdfasdfasdf adds","status":1,"createTime":"2018-10-10 11:38:29","likeTime":0,"postId":48,"floor":10,"nickName":"Asdfasdfasdfasd","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym-AYi72ABK1BJ5YzDg569.jpg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym6AMwBFAAAu5hFH8YQ321.jpg","isLiked":0,"childList":[]},{"commentsId":426,"uid":50,"content":"，？","status":1,"createTime":"2018-10-09 13:56:24","likeTime":0,"postId":48,"floor":9,"nickName":"即将离开了","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu8RDeAcFcsAAnc3nxtoD4899.jpg","isLiked":0,"childList":[]},{"commentsId":403,"uid":48,"content":"Asdfasdfas","status":1,"createTime":"2018-10-08 14:28:07","likeTime":0,"postId":48,"floor":8,"nickName":"Asdfasdfasdfasd","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym-AYi72ABK1BJ5YzDg569.jpg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym6AMwBFAAAu5hFH8YQ321.jpg","isLiked":0,"childList":[]},{"commentsId":375,"uid":39,"content":"嗯呀","status":1,"createTime":"2018-10-02 10:28:11","likeTime":0,"postId":48,"floor":7,"nickName":"yunzhou_bmewi4","avatar":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvLAmACAIyAAULhr__PgY770.jpg","isLiked":0,"childList":[{"childId":377,"childContent":"恩没了","childUid":39,"childName":"yunzhou_bmewi4"},{"childId":376,"childContent":"阿卡丽空","childUid":39,"childName":"yunzhou_bmewi4"}]},{"commentsId":352,"uid":-1,"content":"Ghgfghhn","status":1,"createTime":"2018-09-30 17:18:20","updateTime":"2018-09-30 17:18:20","likeTime":0,"postId":48,"floor":3,"isLiked":1,"childList":[]},{"commentsId":355,"uid":37,"content":"Dbfggghhh","status":1,"createTime":"2018-09-30 17:18:17","likeTime":0,"postId":48,"floor":6,"nickName":"宁财神","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg","isLiked":0,"childList":[]},{"commentsId":353,"uid":37,"content":"Fgfgjjjkk","status":1,"createTime":"2018-09-30 17:18:13","updateTime":"2018-09-30 17:18:13","likeTime":0,"postId":48,"floor":4,"nickName":"宁财神","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg","isLiked":0,"childList":[]},{"commentsId":354,"uid":37,"content":"Fghhgghhbm","status":1,"createTime":"2018-09-30 17:18:08","likeTime":0,"postId":48,"floor":5,"nickName":"宁财神","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg","isLiked":0,"childList":[]},{"commentsId":351,"uid":-1,"content":"Fghn","status":1,"createTime":"2018-09-30 17:01:50","likeTime":0,"postId":48,"floor":2,"isLiked":0,"childList":[]},{"commentsId":298,"uid":47,"content":" 好多家酒店好多话","status":1,"createTime":"2018-09-29 20:34:41","likeTime":0,"postId":48,"floor":1,"nickName":"小熊猫","avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFu-1XmATdhGAATNn5QntKU569.jpg@@http://39.108.61.68:80/group1/M00/00/08/rBKZOFu-1XmAEn18AAAW-_Wnt-0996.jpg","isLiked":0,"childList":[{"childId":343,"childContent":"嗯咯咯哈","childUid":39,"childName":"yunzhou_bmewi4"},{"childId":342,"childContent":"阿奎功能","childUid":39,"childName":"yunzhou_bmewi4","childTargetId":47,"childTarget":"小熊猫"},{"childId":300,"childContent":"好多好多好","childUid":47,"childName":"小熊猫","childTargetId":47,"childTarget":"小熊猫"},{"childId":299,"childContent":"好多好多","childUid":47,"childName":"小熊猫"}]}]}
     */
    @SerializedName("postDatial")
    private Blog blog;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    private CommitsBean commits;


    public CommitsBean getCommits() {
        return commits;
    }

    public void setCommits(CommitsBean commits) {
        this.commits = commits;
    }


    public static class CommitsBean {
        /**
         * isLastPage : false
         * list : [{"commentsId":457,"uid":48,"content":"Asdfasdfasdf adds","status":1,"createTime":"2018-10-10 11:38:29","likeTime":0,"postId":48,"floor":10,"nickName":"Asdfasdfasdfasd","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym-AYi72ABK1BJ5YzDg569.jpg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym6AMwBFAAAu5hFH8YQ321.jpg","isLiked":0,"childList":[]},{"commentsId":426,"uid":50,"content":"，？","status":1,"createTime":"2018-10-09 13:56:24","likeTime":0,"postId":48,"floor":9,"nickName":"即将离开了","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu8RDeAcFcsAAnc3nxtoD4899.jpg","isLiked":0,"childList":[]},{"commentsId":403,"uid":48,"content":"Asdfasdfas","status":1,"createTime":"2018-10-08 14:28:07","likeTime":0,"postId":48,"floor":8,"nickName":"Asdfasdfasdfasd","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym-AYi72ABK1BJ5YzDg569.jpg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Ym6AMwBFAAAu5hFH8YQ321.jpg","isLiked":0,"childList":[]},{"commentsId":375,"uid":39,"content":"嗯呀","status":1,"createTime":"2018-10-02 10:28:11","likeTime":0,"postId":48,"floor":7,"nickName":"yunzhou_bmewi4","avatar":"http://39.108.61.68:80/group1/M00/00/06/rBKZOFuvLAmACAIyAAULhr__PgY770.jpg","isLiked":0,"childList":[{"childId":377,"childContent":"恩没了","childUid":39,"childName":"yunzhou_bmewi4"},{"childId":376,"childContent":"阿卡丽空","childUid":39,"childName":"yunzhou_bmewi4"}]},{"commentsId":352,"uid":-1,"content":"Ghgfghhn","status":1,"createTime":"2018-09-30 17:18:20","updateTime":"2018-09-30 17:18:20","likeTime":0,"postId":48,"floor":3,"isLiked":1,"childList":[]},{"commentsId":355,"uid":37,"content":"Dbfggghhh","status":1,"createTime":"2018-09-30 17:18:17","likeTime":0,"postId":48,"floor":6,"nickName":"宁财神","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg","isLiked":0,"childList":[]},{"commentsId":353,"uid":37,"content":"Fgfgjjjkk","status":1,"createTime":"2018-09-30 17:18:13","updateTime":"2018-09-30 17:18:13","likeTime":0,"postId":48,"floor":4,"nickName":"宁财神","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg","isLiked":0,"childList":[]},{"commentsId":354,"uid":37,"content":"Fghhgghhbm","status":1,"createTime":"2018-09-30 17:18:08","likeTime":0,"postId":48,"floor":5,"nickName":"宁财神","avatar":"http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AMDHVAAAWOfD3G1Q17.jpeg@@http://39.108.61.68:80/group1/M00/00/07/rBKZOFu9Y7-AeCJ5AAAWLn8rqGg58.jpeg","isLiked":0,"childList":[]},{"commentsId":351,"uid":-1,"content":"Fghn","status":1,"createTime":"2018-09-30 17:01:50","likeTime":0,"postId":48,"floor":2,"isLiked":0,"childList":[]},{"commentsId":298,"uid":47,"content":" 好多家酒店好多话","status":1,"createTime":"2018-09-29 20:34:41","likeTime":0,"postId":48,"floor":1,"nickName":"小熊猫","avatar":"http://39.108.61.68:80/group1/M00/00/08/rBKZOFu-1XmATdhGAATNn5QntKU569.jpg@@http://39.108.61.68:80/group1/M00/00/08/rBKZOFu-1XmAEn18AAAW-_Wnt-0996.jpg","isLiked":0,"childList":[{"childId":343,"childContent":"嗯咯咯哈","childUid":39,"childName":"yunzhou_bmewi4"},{"childId":342,"childContent":"阿奎功能","childUid":39,"childName":"yunzhou_bmewi4","childTargetId":47,"childTarget":"小熊猫"},{"childId":300,"childContent":"好多好多好","childUid":47,"childName":"小熊猫","childTargetId":47,"childTarget":"小熊猫"},{"childId":299,"childContent":"好多好多","childUid":47,"childName":"小熊猫"}]}]
         */

        private boolean isLastPage;
        @SerializedName("list")
        private List<PostCommentEntity> postCommentList;

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public List<PostCommentEntity> getPostCommentList() {
            return postCommentList;
        }

        public void setPostCommentList(List<PostCommentEntity> postCommentList) {
            this.postCommentList = postCommentList;
        }

    }
}
