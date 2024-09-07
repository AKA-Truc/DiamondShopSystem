document.addEventListener('DOMContentLoaded', function () {
    const navbar = `
        <div class="navbar">
            <div class="navbar__info">
            </div>
            <div class="navbar__cta">
                <div class="navbar__hello">
                    <ion-icon name="notifications-outline"></ion-icon> 5 Notifications
                </div>
                <div class="navbar__hello">
                    <ion-icon name="settings-outline"></ion-icon> Settings
                </div>
                <div class="navbar__background-icon">
                    <img src="/static/Adimages/account.svg" alt="" />
                </div>
                <div class="navbar__hello">
                    <p>Xin chào</p>
                </div>
            </div>
        </div>
        <div class="navbar-separator"></div>
        <div class="main">
            <!-- Content goes here -->
        </div>
    `;
    document.body.insertAdjacentHTML('afterbegin', navbar);
});
