package vttp.batch5.ssf.day16.models;

public class Info {
    private String query;
    private String limit;
    private String rating;

    @Override
    public String toString() {
        return "Info [query=" + query + ", limit=" + limit + ", rating=" + rating + "]";
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public String getLimit() {
        return limit;
    }
    public void setLimit(String limit) {
        this.limit = limit;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    
}
