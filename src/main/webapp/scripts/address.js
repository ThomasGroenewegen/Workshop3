/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    getAddresses();
});

function getAddresses() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/Workshop3/webresources/address",
        dataType: "json",
        error: function (json, error) {
            console.log(error);
        },
        success: function (data) {
            addressesTable(data);
        }});
}
;

function addressesTable(addresses) {
    $.each(addresses, function (i, address) {
        $("#addresses").append("<tr id='" + i + "'>");
        $("tr#" + i).append("<td>" + address.streetName + "</td>");
        $("tr#" + i).append("<td>" + address.number + "</td>");
        $("tr#" + i).append("<td>" + address.addition + "</td>");
        $("tr#" + i).append("<td>" + address.postalCode + "</td>");
        $("tr#" + i).append("<td>" + address.city + "</td>");
        $("tr#" + i).append("<td>" + address.addressType + "</td>");
        $("tr#" + i).append("<td>" + address.customerId.firstName + ' ' + address.customerId.lastName + "</td>");
        $("tr#" + i).append("<td>" + address.customerId.id + "</td>");
        $("#addresses").append("</tr>");
    });
}


