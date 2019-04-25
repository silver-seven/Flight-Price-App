package com.example.flight;

public class TicketItem {
        private String price;
        private String airline;
        private String origin;
        private String dest;

        public TicketItem(String price, String airline, String origin, String dest) {
            this.price = price;
            this.airline = airline;
            this.origin = origin;
            this.dest = dest;
        }

        public String getPrice() {
            return price;
        }

        public String getAirline() {
            return airline;
        }

        public String getOrigin() {
            return origin;
        }

        public String getDest() {
            return dest;
        }
    }

