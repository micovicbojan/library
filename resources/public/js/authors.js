$(".deleteauthor").click(function () {
    var closestTr = $(this).closest('tr');
    var authorId = $(closestTr).find("td:first").text();
    $.ajax({
        url: '/delete-author',
        type: 'DELETE',
        data: {
            id: authorId
        },
        success: function (result) {
            location.reload();
        },
        error: function () {
           location.reload();
        }
    });
});

$(".updateauthor").click(function (){
 var closestTr = $(this).closest('tr');
  var authorId = $(closestTr).find("td:first").text();
  var newName = document.getElementById("tfName").value;
   $.ajax({
    url: '/update-author',
    type: 'POST',
    data: {id: authorId,
           name: newName
          },
    success: function(result) {
        location.reload();
    },
    error: function(){
       location.reload();
       alert('Error!');
    }
});
});
