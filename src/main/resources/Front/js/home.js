
// קישור לאלמנטים ב-HTML
    var modal = document.getElementById("myModal");
    var btn = document.getElementById("openModalButton");
    var closeBtn = document.getElementsByClassName("close")[0]; // הוספת השורה הזו

    // פתיחת החלונית כאשר לוחצים על הכפתור
    btn.onclick = function() {
        modal.style.display = "block";
    }
    // סגירת החלונית כאשר לוחצים על כפתור
    closeBtn.onclick = function() {
        modal.style.display = "none";
    }
     // סגירת החלונית כאשר לוחצים מחוץ לה
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    // פונקציה לפתיחת המודל
    function openModal() {
        var modal = document.getElementById("myModal");
        modal.style.display = "block";
    }

    // פונקציה לסגירת המודל
    function closeModal() {
        var modal = document.getElementById("myModal");
        modal.style.display = "none";
    }

     // הפונקציה שמתבצעת כאשר לוחצים על הכפתור "שמור"
        function saveData() {
        var status = document.getElementById("status").value;
        var firstName = document.getElementById("firstName").value;
        var lastName = document.getElementById("lastName").value;
        var age = document.getElementById("age").value;
        var height = document.getElementById("height").value;
        var style = document.getElementById("style").value;
        var location = document.getElementById("location").value;
        var seeking = document.getElementById("seeking").value;

        var data = {
            status: status,
            firstName: firstName,
            lastName: lastName,
            age: age,
            height: height,
            style: style,
            location: location,
            seeking: seeking
        };

        fetch('http://localhost:8080/api/men', { // שים לב שהכתובת כאן היא עם הפורט 8080
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
           })
        .then(data => {
               resetFormFields();
               console.log('Data saved successfully:', data);
    })
        .catch(error => {
            console.error('There was an error!', error);
        });
    }

    function resetFormFields() {
        document.getElementById("status").value = "";
        document.getElementById("firstName").value = "";
        document.getElementById("lastName").value = "";
        document.getElementById("age").value = "";
        document.getElementById("height").value = "";
        document.getElementById("style").value = "";
        document.getElementById("location").value = "";
        document.getElementById("seeking").value = "";
    }