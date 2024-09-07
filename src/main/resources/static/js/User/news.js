//
document.getElementById("kienthuc-link").addEventListener("click", function () {
    document.getElementById("dautu-content").classList.remove("active");
    document.getElementById("sukien-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.remove("active");
    document.getElementById("kienthuc-content").classList.add("active");
});

document.getElementById("dautu-link").addEventListener("click", function () {
    document.getElementById("kienthuc-content").classList.remove("active");
    document.getElementById("sukien-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.remove("active");
    document.getElementById("dautu-content").classList.add("active");
});

document.getElementById("sukien-link").addEventListener("click", function () {
    document.getElementById("dautu-content").classList.remove("active");
    document.getElementById("kienthuc-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.remove("active");
    document.getElementById("sukien-content").classList.add("active");
});

document.getElementById("thongtin-link").addEventListener("click", function () {
    document.getElementById("dautu-content").classList.remove("active");
    document.getElementById("kienthuc-content").classList.remove("active");
    document.getElementById("sukien-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.add("active");
});

document.getElementById("khuyenmai-link").addEventListener("click", function () {
    document.getElementById("dautu-content").classList.remove("active");
    document.getElementById("kienthuc-content").classList.remove("active");
    document.getElementById("sukien-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.add("active");
});