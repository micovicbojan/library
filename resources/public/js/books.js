$(".deletebook").click(function () {
    var closestTr = $(this).closest('tr');
    var bookId = $(closestTr).find("td:first").text();
    $.ajax({
        url: '/delete-book',
        type: 'DELETE',
        data: {
            id: bookId
        },
        success: function (result) {
            window.location.href = "/book";
        }
    });
});

$(".updatebook").click(function (){

  var closestTr = $(this).closest('tr');
  var bookId = $(closestTr).find("td:first").text();
  var name = $('#bookName').val();
  var yearOfPublishing = $('#bookYearOfPublishing').val();

  var selectedAuthor = $("#authors option:selected").val();
  var selectedPublisher = $("#publishers option:selected").val();

   $.ajax({
    url: '/update-book',
    type: 'POST',
    data: {id: bookId,
           name: name,
           yr_of_pub: yearOfPublishing,
           author_id: selectedAuthor,
           publisher_id: selectedPublisher
          },
    success: function(result) {
        window.location.href = "/book";
    }
});
});

function insertbook(authorsList,publishersList){
  var bookId = $('#bookId').val();
  var name = $('#bookName').val();
  var yearOfPublishing = $('#bookYearOfPublishing').val();

  var authIndex = authorsList.selectedIndex;
  var selectedAuthor = authorsList.options[authIndex].value;

  var pubIndex = publishersList.selectedIndex;
  var selectedPublisher = publishersList.options[pubIndex].value;

   $.ajax({
    url: '/save-book',
    type: 'POST',
    data: {id: bookId,
           name: name,
           yr_of_pub: yearOfPublishing,
           author_id: selectedAuthor,
           publisher_id: selectedPublisher
          },
    success: function(result) {
        window.location.href = "/book";
    }
});
}
