var modal = document.getElementById("menModal");

var btn = document.getElementById("openModalButton");
var closeBtn = document.getElementsByClassName("close")[0];

function openMenModal() {
    var modal = document.getElementById("menModal");
    modal.style.display = "block";
}

function openWomenModal() {
    var modal = document.getElementById("womenModal");
    modal.style.display = "block";
}

function closeModal(modalId) {
    var modal = document.getElementById(modalId);
    modal.style.display = "none";
}

closeBtn.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function saveMenData() {
    var status = document.getElementById("menStatus").value;
    var firstName = document.getElementById("menFirstName").value;
    var lastName = document.getElementById("menLastName").value;
    var age = document.getElementById("menAge").value;
    var height = document.getElementById("menHeight").value;
    var location = document.getElementById("menLocation").value;
    var style = document.getElementById("menStyle").value;
    var seeking = document.getElementById("menSeeking").value;

    var data = {
        status: status,
        firstName: firstName,
        lastName: lastName,
        age: age,
        height: height,
        location: location,
        style: style,
        seeking: seeking
    };

    fetch('http://localhost:8080/api/men', {
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

function saveWomenData() {
    var status = document.getElementById("womenStatus").value;
    var firstName = document.getElementById("womenFirstName").value;
    var lastName = document.getElementById("womenLastName").value;
    var age = document.getElementById("womenAge").value;
    var height = document.getElementById("womenHeight").value;
    var location = document.getElementById("womenLocation").value;
    var style = document.getElementById("womenStyle").value;
    var seeking = document.getElementById("womenSeeking").value;

    var data = {
        status: status,
        firstName: firstName,
        lastName: lastName,
        age: age,
        height: height,
        location: location,
        style: style,
        seeking: seeking
    };

    fetch('http://localhost:8080/api/women', {
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
    document.getElementById("menStatus").value = "";
    document.getElementById("menFirstName").value = "";
    document.getElementById("menLastName").value = "";
    document.getElementById("menAge").value = "";
    document.getElementById("menHeight").value = "";
    document.getElementById("menLocation").value = "";
    document.getElementById("menStyle").value = "";
    document.getElementById("menSeeking").value = "";

    document.getElementById("womenStatus").value = "";
    document.getElementById("womenFirstName").value = "";
    document.getElementById("womenLastName").value = "";
    document.getElementById("womenAge").value = "";
    document.getElementById("womenHeight").value = "";
    document.getElementById("womenLocation").value = "";
    document.getElementById("womenStyle").value = "";
    document.getElementById("womenSeeking").value = "";
}
//לחצן חפש
    // שליחת בקשה לשרת עם הפרמטרים הנדרשים
   function search() {
       const searchTerm = document.getElementById('searchInput').value;
       const gender = document.querySelector('input[name="gender"]:checked').value;
       const criteria = document.getElementById('searchCriteria').value;

       // קביעה של הנתיב הנכון על פי בחירת המגדר
       const url = gender === 'men' ? 'http://localhost:8080/api/men/search' : 'http://localhost:8080/api/women/search';

       console.log(`Fetching: ${url}?term=${searchTerm}&criteria=${criteria}`); // הוסף לוג זה

       // שליחת בקשה לשרת עם הפרמטרים הנדרשים
       fetch(`${url}?term=${searchTerm}&criteria=${criteria}`)
           .then(response => {
               if (!response.ok) {
                   throw new Error(`HTTP error! status: ${response.status}`);
               }
               return response.json();
           })
           .then(data => {
               // ניקוי הטבלה לפני הצגת התוצאות החדשות
               const tableBody = document.querySelector('#resultsTable tbody');
               tableBody.innerHTML = '';

               if (data.length > 0) {
                   data.forEach(result => {
                       const row = document.createElement('tr');
                       row.innerHTML = `
                       <td>${result.height}</td>
                       <td>${result.status}</td>
                       <td>${result.location}</td>
                       <td>${result.age}</td>
                       <td>${result.lastName}</td>
                       <td>${result.firstName}</td>
                       <td>${result.id}</td>
                       `;
                       tableBody.appendChild(row);
                   });
               }
           })
           .catch(error => {
               console.error('Error fetching search results:', error);
           });
   }
