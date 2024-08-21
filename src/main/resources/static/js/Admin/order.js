document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    const fetchAndDisplayInvoices = () => {
        fetch('http://localhost:8080/api/invoice/getAllInvoice')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Data fetched:', data);

                const invoices = data;

                if (!Array.isArray(invoices)) {
                    throw new Error('Expected an array of invoices');
                }

                const invoiceList = document.getElementById('invoice-list');
                if (!invoiceList) {
                    throw new Error('Invoice list element not found');
                }

                invoiceList.innerHTML = ''; // Clear previous rows

                invoices.forEach((invoice, index) => {
                    const row = document.createElement('tr');
                    console.log(invoice.Voucher); // This should print voucher details

                    const voucherInfo = invoice.Voucher
                        ? `${invoice.Voucher.Name} - ${invoice.Voucher.Describes}`
                        : 'Không Áp Dụng';

                    const formattedTotalCost = invoice.Totalcost.toLocaleString(); // Format total cost

                    row.innerHTML = `
                        <td class="editable">${index + 1}</td>
                        <td class="editable">${invoice.InvoiceID}</td>
                        <td class="editable">${invoice.Customer ? invoice.Customer.Name : 'N/A'}</td>
                        <td class="editable">${invoice.InvoiceDate}</td>
                        <td class="editable">${voucherInfo}</td>
                        <td class="editable">${formattedTotalCost}</td>
                        <td class="action-buttons">
                            <button class="edit-btn"><ion-icon name="eye-outline"></ion-icon></button>
                        </td>
                    `;

                    invoiceList.appendChild(row);

                    // Add click event listener to edit button
                    const editBtn = row.querySelector('.edit-btn');
                    editBtn.addEventListener('click', () => {
                        // Redirect to invoice detail page
                        window.location.href = `./bill.html?InvoiceID=${invoice.InvoiceID}`;
                    });
                });
            })
            .catch(error => console.error('Error fetching or displaying invoices:', error.message));
    };

    // Initial fetch and display
    fetchAndDisplayInvoices();
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');

//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });