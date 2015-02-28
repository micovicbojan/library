$(".deletepublisher").click(function () {
    var closestTr = $(this).closest('tr');
    var publisherId = $(closestTr).find("td:first").text();
    $.ajax({
        url: '/delete-publisher',
        type: 'DELETE',
        data: {
            id: publisherId
        },
        success: function (result) {
            location.reload();
        },
        error: function () {
           location.reload();
        }
    });
});

$(".updatepublisher").click(function (){
 var closestTr = $(this).closest('tr');
  var publisherId = $(closestTr).find("td:first").text();
  var newName = document.getElementById("tfName").value;
   $.ajax({
    url: '/update-publisher',
    type: 'POST',
    data: {id: publisherId,
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
