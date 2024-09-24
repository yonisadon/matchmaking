//class UIService {
//    static displayResults(data, tableBodyId, columns, gender) {
//        console.log("test", gender);
//
//        const tableBody = document.querySelector(`#${tableBodyId} tbody`);
//        tableBody.innerHTML = ''; // נקה את התוכן הקודם בטבלה
//
//        // אם הנתון אינו מערך, הפוך אותו למערך עם אובייקט בודד
//        if (!Array.isArray(data)) {
//            data = [data];
//        }
//
//        console.log("Data to display:", data);
//
//        // יצירת שורות עבור כל נתון
//        data.forEach(result => {
//            const row = document.createElement('tr');
//            console.log("Processing result:", result);
//
//            columns.forEach(column => {
//                const cell = document.createElement('td');
//
//                // טיפול מיוחד עבור גיל וגובה, רק אם קיימים שדות אלו בנתונים
//                if (column === 'ageRange' && result.preferredAgeFrom !== undefined && result.preferredAgeTo !== undefined) {
//                    const ageRange = `${result.preferredAgeFrom || ''}-${result.preferredAgeTo || ''}`;
//                    cell.textContent = ageRange.trim() !== '-' ? ageRange : '';
//                } else if (column === 'heightRange' && result.preferredHeightFrom !== undefined && result.preferredHeightTo !== undefined) {
//                    const heightRange = `${result.preferredHeightFrom || ''}-${result.preferredHeightTo || ''}`;
//                    cell.textContent = heightRange.trim() !== '-' ? heightRange : '';
//                } else {
//                    // לכל שאר השדות
//                    cell.textContent = result[column] !== undefined ? result[column] : '';
//                }
//
//                row.appendChild(cell);
//            });
//
//            console.log("Selected Gender before selectRecord:", gender);
//
//            // הוספת ה-`onclick` עבור כל שורה
//            row.onclick = () => selectRecord(result.id, gender, row); // gender יישלח גם פה
//            tableBody.appendChild(row);
//        });
//    }
//}


class UIService {

    static displayResults(data, tableBodyId, columns, gender) {
    console.log("test", gender);

        const tableBody = document.querySelector(`#${tableBodyId} tbody`);
        tableBody.innerHTML = ''; // נקה את התוכן הקודם בטבלה
    console.log("test1", gender);

        // יצירת שורות עבור כל נתון
        if (data.length > 0) {
            data.forEach(result => {
                const row = document.createElement('tr');
    console.log("test2", gender);

                // יצירת תאים לכל שדה בטבלה בהתאם ל-columns
                columns.forEach(column => {
                    const cell = document.createElement('td');
                    cell.textContent = result[column] !== undefined ? result[column] : '';
                    row.appendChild(cell);
                });
console.log("Selected Gender before selectRecord:", gender);
                // הוספת ה-`onclick` עבור כל שורה
                row.onclick = () => selectRecord(result.id, gender, row); // gender יישלח גם פה
                tableBody.appendChild(row);
            });

        }
    }
}
