
class SelectionService {
    static selectRecord(id, gender, row) {
        const allRows = row.closest('tbody').querySelectorAll('tr');
        allRows.forEach(row => row.classList.remove('selected'));
        row.classList.add('selected');
        // שאר הלוגיקה
    }
}
