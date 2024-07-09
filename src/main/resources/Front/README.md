Controller: אחראי לטיפול בבקשות HTTP ולשלוח תגובות ללקוח.
Service: מכיל את הלוגיקה העסקית, ומטפל בשיחות לשכבת ה-Repository.
Repository: מתקשר עם מסד הנתונים ומבצע את השאילתות הנדרשות.
Model: מייצג את מבנה הנתונים של האובייקטים במערכת.


table tbody tr:hover {
background-color: #f1f1f1;
}



function openMenModal() {
var modal = document.getElementById("menModal");
modal.style.display = "block";
}

function openWomenModal() {
var modal = document.getElementById("womenModal");
modal.style.display = "block";
}

closeBtn.onclick = function() {
modal.style.display = "none";
}
