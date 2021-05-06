$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
// Form validation-------------------
/*var status = validateItemForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }*/
// If valid------------------------
var type = ($("#hidProductIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "ProductManagementServlet",
 type : type,
 data : $("#formProduct").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
 onItemSaveComplete(response.responseText, status);
 }
 });
});

////////////////////////////////////////////////////////////////////////////////

function onItemSaveComplete(response, status)
{ 
    if (status == "success") 
    { 
        var resultSet = JSON.parse(response); 
        if (resultSet.status.trim() == "success") 
        { 
            $("#alertSuccess").text("Successfully saved."); 
            $("#alertSuccess").show(); 
            $("#divUsersGrid").html(resultSet.data); 
        } 
        else if (resultSet.status.trim() == "error") 
        { 
            $("#alertError").text(resultSet.data); 
            $("#alertError").show(); 
        } 
    } 
    else if (status == "error") 
    { 
        $("#alertError").text("Error while saving."); 
        $("#alertError").show(); 
    } else
    { 
        $("#alertError").text("Unknown error while saving.."); 
        $("#alertError").show(); 
    }
    $("#hidProductIDSave").val(""); 
    $("#formProduct")[0].reset(); 
}

 

$(document).on("click", ".btnUpdate", function(event)
{ 
    $("#hidProductIDSave").val($(this).data("productid"));
    $("#research_id").val($(this).closest("tr").find('td:eq(1)').text()); 
    $("#name").val($(this).closest("tr").find('td:eq(2)').text()); 
    $("#description").val($(this).closest("tr").find('td:eq(3)').text()); 
    $("#stock_quantity").val($(this).closest("tr").find('td:eq(4)').text());
    $("#price").val($(this).closest("tr").find('td:eq(5)').text());
    $("#added_date").val($(this).closest("tr").find('td:eq(6)').text());
   
    
});



$(document).on("click", ".btnRemove", function(event)
{ 
    $.ajax( 
    { 
        url : "ProductManagementServlet", 
        type : "DELETE", 
        data : "products_id=" + $(this).data("productid"),
        dataType : "text", 
        complete : function(response, status) 
        { 
            onItemDeleteComplete(response.responseText, status); 
        } 
    }); 
})

 

function onItemDeleteComplete(response, status)
{ 
if (status == "success") 
{ 
    var resultSet = JSON.parse(response); 
    if (resultSet.status.trim() == "success") 
    { 
        $("#alertSuccess").text("Successfully deleted."); 
        $("#alertSuccess").show(); 
        $("#divUsersGrid").html(resultSet.data); 
    } 
    else if (resultSet.status.trim() == "error") 
    { 
        $("#alertError").text(resultSet.data); 
        $("#alertError").show(); 
    } 
     } 
    else if (status == "error") 
    { 
        $("#alertError").text("Error while deleting."); 
        $("#alertError").show(); 
    } 
    else
    { 
        $("#alertError").text("Unknown error while deleting.."); 
        $("#alertError").show(); 
    }
} 