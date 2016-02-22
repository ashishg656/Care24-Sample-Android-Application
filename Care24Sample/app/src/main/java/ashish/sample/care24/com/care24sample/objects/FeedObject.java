package ashish.sample.care24.com.care24sample.objects;

import java.util.List;

/**
 * Created by ashis_000 on 2/22/2016.
 */
public class FeedObject {

    long result_count;
    List<FeedSingleObject> data;

    public class FeedSingleObject {
        String id, uri, title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public long getResult_count() {
        return result_count;
    }

    public void setResult_count(long result_count) {
        this.result_count = result_count;
    }

    public List<FeedSingleObject> getData() {
        return data;
    }

    public void setData(List<FeedSingleObject> data) {
        this.data = data;
    }
}
