function prefixVerificationRequest(event) {
    event.preventDefault();
    const data = {
        phoneNumber: $("#phoneNumber").val(),
    };

jQuery.ajax({
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    'type': 'POST',
    'url': "api/v1/country-by-phone",
    'data': JSON.stringify(data),
    'dataType': 'json',
    'success': onSuccess,
    'error': onError
  });

  $("#phoneNumber").val('')
  }

function onError(xhr, status, error) {
      var responseText;
      $("#status").html("");
      responseText = jQuery.parseJSON(xhr.responseText);

      for (let i = 0; i < responseText.length; i++) {
        generateException(responseText, i)
      }
}

function onSuccess(data, textStatus, xhr) {
      $("#status").html("");
      if(data.length === 0) {
        $("#status").append("<br><div>OOPS. Cant find country by your phone number.</div>");
      } else {
        $("#status").append("<br><div>Your " + countries(data) + data + ".</div>");
      }
}

function countries(data) {
    if(data.length > 1) {
         return 'countries are: ';
    } else {
        return 'country is: ';
    }
}

function generateException(responseText, exceptionIndex) {
    $("#status").append("<div style='color: red'>" + responseText[exceptionIndex].errorMessage + "</div><br>");
}

