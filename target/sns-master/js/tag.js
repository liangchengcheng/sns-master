var tags = new Array();
$(document).ready(function () {
    $(document).on('click','delete.icon',function (event) {
        var rmtag = $(this).parent('.ui.label').text();
        for (var i=0;i<tags.length;i++){
            if (tags[i] == rmtag){
                //删除元素,然后添加元素。(1就代表删除+添加=替换)
                tags.splice(i,1);
                $(this).parent('.ui.label').remove();
            }
        }
    });
    
    $('#tag-input').keyup(function (event) {
        var tag_input = $('#tag-input').val();
        var length = tag_input.length;
        if (length != 0 && tag_input.lastIndexOf(' ')==(length -1 )){
            var tag = $.trim(tag_input);
            if (tag.length != 0){
                tags.push(tag);
                var newtag = $('<div class="ui label">'+tag+'<i class="delete icon"></i></div>');
                $('.tagfield').append(newtag);
                $('#tag-input').val('');
            }
        }
    });
});