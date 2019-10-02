/*
* 提交回复
* */
function post() {
    var id = $("#question_id").val();
    var content = $("#comment-content").val();
    commentTarget(id, content, 1);
}

/**
 * 展开二级评论
 */
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active")
        } else {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data.data);
                $.each(data.data.reverse(), function (index, comment) {
                    /*var avatarElement = $("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    });*/
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    })).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format("YYYY-MM-DD")
                    }));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);


                    var contentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 comments"
                    }).append(mediaElement);


                    subCommentContainer.prepend(contentElement);
                });
                comments.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active")
            });
        }
    }
}

function commentTarget(id, content, type) {
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": id,
            "content": content,
            "type": type
        }),
        dataType: "json",
        success: function (result) {
            if (result.code == 200) {
                /* $("#comment_section").hide();*/
                window.location.reload();
            } else {
                if (result.code == 2003) {
                    var conf = confirm(result.message);
                    if (conf) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.2d15475aa762bf73&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(result.message);
                }
            }
        }

    })
}


function comment(e) {
    var id = e.getAttribute("data-id");
    var content = $("#input-" + id).val();
    commentTarget(id, content, 2)

}


function selectTags(e) {
    var value = e.getAttribute("data-tag");
    var tags = $("#tag").val();
    if (tags.indexOf(value) == -1) {
        if (tags) {
            $("#tag").val(tags + "," + value);
        } else {
            $("#tag").val(value);
        }

    }
}

function selectTab() {
    $("#tag-tab").show();
}