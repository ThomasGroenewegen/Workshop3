/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    getProducts();
});

function getProducts() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/Workshop3/webresources/product",
        dataType: "json",
        error: function (json, error) {
            console.log(error);
        },
        success: function (data) {
            productsTable(data);
        }});
}
;

function productsTable(products) {
    $.each(products, function (i, product) {
        $("#products").append("<tr id='" + i + "'>");
        $("tr#" + i).append("<td>" + product.id + "</td>");
        $("tr#" + i).append("<td>" + product.name + "</td>");
        $("tr#" + i).append("<td>" + "\u20ac" + product.price + "</td>");
        $("tr#" + i).append("<td>" + product.stock + "</td>");
        $("tr#" + i).append("<td>" + product.productStatus + "</td>");
        $("#products").append("</tr>");
    });
}