document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".search-input").forEach(input => {
        input.addEventListener("input", function () {
            const search = this.value.toLowerCase();
            const containerSelector = this.dataset.target;
            const cards = document.querySelectorAll(`${containerSelector} .card`);

            cards.forEach(card => {
                const text = Array.from(card.querySelectorAll('[data-searchable]'))
                    .map(el => el.textContent.toLowerCase())
                    .join(' ');
                card.style.display = text.includes(search) ? '' : 'none';
            });
        });
    });
});