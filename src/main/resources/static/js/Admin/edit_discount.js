async function fetchAndUpdateVoucherDetails(voucherId) {
    try {
        const response = await fetch(`http://localhost:8080/api/voucher/getVoucher/${voucherId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch voucher details');
        }
        const data = await response.json();

        console.log('Dữ liệu từ API:', data); // Kiểm tra dữ liệu từ API

        if (data.success && data.voucher) {
            const voucher = data.voucher;

            document.getElementById('Name').value = voucher.Name || '';
            document.getElementById('Percent').value = voucher.Percent || '';
            document.getElementById('Describes').value = voucher.Describes || '';

            // Xử lý EXDate để đảm bảo đúng định dạng yyyy-MM-ddThh:mm
            if (voucher.EXDate) {
                const exDate = new Date(voucher.EXDate);
                const formattedExDate = exDate.toISOString().slice(0, 16);
                document.getElementById('EXDate').value = formattedExDate;
            } else {
                document.getElementById('EXDate').value = '';
            }

            document.getElementById('Mincost').value = voucher.Mincost || '';
            document.getElementById('Maxcost').value = voucher.Maxcost || '';
        }
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu từ API:', error);
        alert('Failed to fetch voucher details');
    }
}

async function handleVoucherFormSubmit(event) {
    event.preventDefault();

    const voucherId = new URLSearchParams(window.location.search).get('id');
    const updatedVoucher = {
        Name: document.getElementById('Name').value,
        Percent: document.getElementById('Percent').value,
        Describes: document.getElementById('Describes').value,
        EXDate: document.getElementById('EXDate').value,
        Mincost: document.getElementById('Mincost').value,
        Maxcost: document.getElementById('Maxcost').value
    };
    console.log(updatedVoucher);

    try {
        const response = await fetch(`http://localhost:8080/api/voucher/updateVoucher/${voucherId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedVoucher)
        });


        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log(data);
        alert('Voucher updated successfully!');
        window.location.href = '../magiamgia/mgg.html'; // Change to your redirect URL
    } catch (error) {
        console.error('Error updating voucher:', error);
        alert('Failed to update voucher');
    }
}

function handleCancel() {
    if (confirm('Bạn có chắc muốn hủy không?')) {
        window.location.href = '../magiamgia/mgg.html';
    }
}

document.addEventListener('DOMContentLoaded', async function() {
    const urlParams = new URLSearchParams(window.location.search);
    const voucherId = urlParams.get('id');

    if (voucherId) {
        await fetchAndUpdateVoucherDetails(voucherId);
    }

    document.getElementById('voucherForm').addEventListener('submit', handleVoucherFormSubmit);
    document.querySelector('.cancel').addEventListener('click', handleCancel);
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');
//
//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });