# cinema_ticket_booking_app
This project implements a `TicketService` that handles the purchase of tickets, calculates the payment amount, and reserves seats based on certain business rules and constraints.

## Business Rules

- There are 3 types of tickets: Infant, Child, and Adult.
- Ticket prices vary based on the ticket type.
- The ticket purchaser declares the number and type of tickets they want to buy.
- A maximum of 20 tickets can be purchased at a time.
- Infants do not pay for a ticket and are not allocated a seat.
- Child and Infant tickets cannot be purchased without an Adult ticket.

## Ticket Types and Prices

| Ticket Type | Price   |
| ------------| ------- |
| INFANT      | £0      |
| CHILD       | £10     |
| ADULT       | £20     |

## Constraints

- The `TicketService` interface cannot be modified.
- Code in the `thirdparty.*` packages cannot be modified.
- `TicketTypeRequest` should be an immutable object.

## Assumptions

- Accounts with an ID greater than zero are valid and have sufficient funds.
- `TicketPaymentService` and `SeatReservationService` are external providers without defects.
- Payments and seat reservations will always go through.

## Implementation Details

The project provides a working implementation of the `TicketService` interface that:

- Considers the defined business rules, constraints, and assumptions.
- Calculates the correct amount for requested tickets and initiates a payment request to `TicketPaymentService`.
- Determines the correct number of seats to reserve and makes a reservation request to `SeatReservationService`.
- Rejects any invalid ticket purchase requests.

## Getting Started

Prerequisite:

1. Java 17 should be installed in the host machine
2. Maven should be installed in the host machine

## Setup

Follow these steps to set up and run the project locally:

1. **Clone the Repository:**

    ```bash
    git clone https://github.com/imraaimmu/cinema_ticket_booking_app.git
    ```
   ```bash
    cd cinema_ticket_booking_app
   ```

2. **Run Using Maven:**

    ```bash
    mvn test
    ```

Make sure you have [Maven](https://maven.apache.org/) installed on your machine.
