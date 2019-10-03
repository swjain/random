
function solution(D, T) {
    const D = '2016-11-30';
    const T = 14;
    const givenDate = new Date(D);
    const retentionPeriod = T * 86400000;
    let incorrectlyMarkedCustomers = 0;

    const containsRedBg = (r) => {
        return $(r).css('background-color') == 'rgb(255, 0, 0)';
    }

    $('table').each((i, t) => {
        $(t).find('tr').each((i, r) => {

            const row = $(r);
            const data = $(r).children('td');
            const name = $(data[0]).text();

            let borrowedDate = $(data[1]).text();
            let returnDate = $(data[2]).text();
            borrowedDate = new Date(borrowedDate);
            returnDate = returnDate && new Date(returnDate);

            const expectedReturnDate = new Date(borrowedDate.valueOf() + retentionPeriod);
            let isOverdue = false;

            if (returnDate) {
                if (returnDate > expectedReturnDate) {
                    isOverdue = true;
                }
            } else {
                if (givenDate > expectedReturnDate) {
                    isOverdue = true;
                }
            }

            const markedRed = containsRedBg(row);
            if (isOverdue != markedRed) {
                incorrectlyMarkedCustomers++;
            }

        });
    });
    return incorrectlyMarkedCustomers;
}