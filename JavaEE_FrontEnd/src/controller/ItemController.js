//crud

generateItemId();
loadAllItems();

$("#btnItemSave").click(function () {
    saveItem();
    clearAll();
    loadAllItems();
    generateItemId();
    loadAllItemCodes();

});


function saveItem() {
    let itemCode = $("#txtItemCode").val();
    let itemName = $("#txtItemName").val();
    let itemQTY = $("#txtItemQTY").val();
    let unitPrice = $("#txtUnitPrice").val();

    //create Object
    /* var itemObject = {
         code: itemCode,
         name: itemName,
         qTY: itemQTY,
         unitPrice: unitPrice
     };
 */
    var itemObject = new Item(itemCode, itemName, itemQTY, unitPrice);
    itemDB.push(itemObject);
}


/*_________Update Customer___________*/
$("#btnItemUpdate").click(function () {
    let itemId = $("#txtItemCode").val();
    let itemName = $("#txtItemName").val();
    let itemQty = $("#txtItemQTY").val();
    let itemPrice = $("#txtUnitPrice").val();

    for (var i = 0; i < itemDB.length; i++) {
        if (itemDB[i].getItemCode() == itemId) {
            itemDB[i].setItemName(itemName);
            itemDB[i].setItemQTY(itemQty);
            itemDB[i].setUnitPrice(itemPrice);
        }
    }
    loadAllItems();
    clearAll();
    generateItemId();
});

/*_________Delete Item___________*/
function deleteItem() {
    $("#btnItemDelete").click(function () {
        let getClickItemData = $("#txtItemCode").val();
        for (let i = 0; i < itemDB.length; i++) {
            if (itemDB[i].getItemCode() == getClickItemData) {
                itemDB.splice(i, 1);
            }
        }
        loadAllItems();
        clearAll();
        generateItemId();

    });
}

/*_________clear button___________*/
$("#btnItemClear").click(function () {
    clearAll();
});


function bindItem() {
    /*_________click Item Table ___________*/
    $("#itemTB > tr").click(function () {
        let itemCode = $(this).children(":eq(0)").text();
        let itemName = $(this).children(":eq(1)").text();
        let itemQty = $(this).children(":eq(2)").text();
        let unitPrice = $(this).children(":eq(3)").text();

        /*_________set data for text fields__________*/
        $("#txtItemCode").val(itemCode);
        $("#txtItemName").val(itemName);
        $("#txtItemQTY").val(itemQty);
        $("#txtUnitPrice").val(unitPrice);

    });
}

function loadAllItems() {
    $("#itemTB").empty();
    $.ajax({
      url: "http://localhost:8080/JavaEE_BackEnd/item?option=GETALL",
        method:"GET",
        success: function (resp){
          for (const item of resp.data){
              let row = `<tr><td>${item.code}</td><td>${item.description}</td><td>${item.qtyOnHand}</td><td>${item.unitPrice}</td></tr>`;
              $("#itemTB").append(row);
              bindItem();
              deleteItem();
          }
        }
    });

   /* for (var i of itemDB) {
        let row = `<tr><td>${i.getItemCode()}</td><td>${i.getItemName()}</td><td>${i.getItemQTY()}</td><td>${i.getUnitPrice()}</td></tr>`;
        $("#itemTB").append(row);

        bindItem();
        deleteItem();
    }*/
}

$("#btnItemSearch").click(function () {

    $.ajax({
       url:"http://localhost:8080/JavaEE_BackEnd/item?option=SEARCH",
        method:"GET",
        data:{
           code: $("#btnItemSearch").val()
        },
        success: function (resp){
           if (resp.status==200){

            $("#itemTB").empty();
            for (const item of resp.data){
                let row = `<tr><td>${item.code}</td><td>${item.description}</td><td>${item.qtyOnHand}</td><td>${item.unitPrice}</td></tr>`;
                $("#itemTB").append(row);
                bindItem();
                deleteItem();
            }
        }else {
             alert(resp.data);
             loadAllItems();
             clearAll();
           }
        }
    });
   /* var searchItemCode = $("#txtSearchItemCode").val();

    var response = searchItem(searchItemCode);
    if (response) {
        $("#txtItemCode").val(response.getItemCode());
        $("#txtItemName").val(response.getItemName());
        $("#txtItemQTY").val(response.getItemQTY());
        $("#txtUnitPrice").val(response.getUnitPrice());
    } else {
        clearAll();
        alert("No Such a item");
    }*/
});

/*
function searchItem(id) {
    for (let i = 0; i < itemDB.length; i++) {
        if (itemDB[i].id == id) {
            return itemDB[i];
        }
    }
}*/

function clearAll() {
    $("#txtItemCode,#txtItemName,#txtItemQTY,#txtUnitPrice,#txtSearchItemCode").val("");    // Clear input Fields (Add)
}

function generateItemId() {

    $.ajax({
       url: "http://localhost:8080/JavaEE_BackEnd/item?option=GENID",
       method:"GET",
        success: function (resp){
           if (resp.status==200){
               $("#txtItemCode").val(resp.data.code);
           }else{
               alert(resp.data);
           }
        }
    });
  /*  let index = itemDB.length - 1;
    let id;
    let temp;
    if (index != -1) {
        id = itemDB[itemDB.length - 1].getItemCode();
        temp = id.split("-")[1];
        temp++;
    }

    if (index == -1) {
        $("#txtItemCode").val("I00-001");
    } else if (temp <= 9) {
        $("#txtItemCode").val("I00-00" + temp);
    } else if (temp <= 99) {
        $("#txtItemCode").val("I00-0" + temp);
    } else {
        $("#txtItemCode").val("I00-" + temp);
    }*/
}
