import http from "k6/http";
import {parseHTML} from "k6/html";

export default function() {
    const res = http.get("https://uece.br");
    const doc = parseHTML(res.body);
    doc.find("link").toArray().forEach(function (item) {
        console.log(item.attr("href"));
     });
}