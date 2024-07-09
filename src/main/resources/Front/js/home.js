var modal = document.getElementById("menModal");

var btn = document.getElementById("openModalButton");

function closeModal(modalId) {
var modal = document.getElementById(modalId);
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
  document.addEventListener('DOMContentLoaded', function() {
      let selectedRecordId = null;
      let selectedGender = null;

  function search() {
      const searchTerm = document.getElementById('searchInput').value;
      const gender = document.querySelector('input[name="gender"]:checked').value;
      const criteria = document.getElementById('searchCriteria').value;

      let url;
      if (criteria === 'all') {
          url = gender === 'men' ? 'http://localhost:8080/api/men/searchAll' : 'http://localhost:8080/api/women/searchAll';
      } else {
          url = gender === 'men' ? `http://localhost:8080/api/men/search?term=${searchTerm}&criteria=${criteria}` : `http://localhost:8080/api/women/search?term=${searchTerm}&criteria=${criteria}`;
      }

      console.log(`Fetching: ${url}`);
      fetch(url)
          .then(response => {
              if (!response.ok) {
                  throw new Error(`HTTP error! status: ${response.status}`);
              }
              return response.json();
          })
          .then(data => {
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
                      row.onclick = () => selectRecord(result.id, gender, row);
                      tableBody.appendChild(row);
                  });
              }
          })
          .catch(error => {
              console.error('Error fetching search results:', error);
          });
  }

  function selectRecord(id, gender, row) {
      selectedRecordId = id;
      selectedGender = gender;

      // הסרת בחירה מכל השורות
      const rows = document.querySelectorAll('#resultsTable tbody tr');
      rows.forEach(r => r.classList.remove('selected'));

      // הוספת מחלקת "נבחר" לשורה שנבחרה
      row.classList.add('selected');
  }

  function deleteSelected() {
      if (!selectedRecordId) {
          alert("אנא בחר רשומה למחיקה.");
          return;
      }

      const url = selectedGender === 'men' ? `http://localhost:8080/api/men/${selectedRecordId}` : `http://localhost:8080/api/women/${selectedRecordId}`;

      fetch(url, {
          method: 'DELETE'
      })
      .then(response => {
          if (!response.ok) {
              throw new Error(`HTTP error! status: ${response.status}`);
          }
          console.log(`Record with id ${selectedRecordId} deleted successfully`);
          search(); // רענן את תוצאות החיפוש לאחר המחיקה
          selectedRecordId = null; // איפוס ה-ID הנבחר לאחר המחיקה
          selectedGender = null; // איפוס המגדר הנבחר לאחר המחיקה
      })
      .catch(error => {
          console.error('Error deleting record:', error);
      });
  }

  function updateSelected() {
        if (!selectedRecordId) {
            alert("אנא בחר רשומה לעדכון.");
            return;
        }

        const url = selectedGender === 'men' ? `http://localhost:8080/api/men/${selectedRecordId}` : `http://localhost:8080/api/women/${selectedRecordId}`;

        fetch(url, {
            method: 'PUT'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

        })
        .catch(error => {
            console.error('Error deleting record:', error);
        });
    }

 function closeModal(modalId) {
        const modal = document.getElementById(modalId);
        modal.style.display = "none";
    }

    function openMenModal() {
        document.getElementById('menModal').style.display = 'block';
    }

    function openWomenModal() {
        document.getElementById('womenModal').style.display = 'block';
    }

    window.search = search;
    window.selectRecord = selectRecord;
    window.deleteSelected = deleteSelected;
    window.updateSelected = updateSelected;
    window.closeModal = closeModal;
    window.openMenModal = openMenModal;
    window.openWomenModal = openWomenModal;
});