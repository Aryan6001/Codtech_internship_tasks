import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.ItemSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommendationSystem {

    public static void main(String[] args) throws TasteException, IOException {
        // Step 1: Load the sample data model (user-item interactions with ratings)
        DataModel model = new FileDataModel(new File("user_ratings.csv"));
        
        // Step 2: Use a similarity metric (Pearson Correlation for User-Based)
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        
        // Step 3: Create a recommender based on User-based collaborative filtering
        Recommender recommender = new GenericUserBasedRecommender(model, similarity, 5);
        
        // Step 4: Recommend 3 items for user 1
        List<RecommendedItem> recommendations = recommender.recommend(1, 3);
        
        // Step 5: Display the recommendations
        System.out.println("Recommendations for User 1:");
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("Product ID: " + recommendation.getItemID() + ", Estimated Rating: " + recommendation.getValue());
        }
        
        // For Item-Based Collaborative Filtering:
        // Create item-based recommender
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(model);
        GenericItemBasedRecommender itemRecommender = new GenericItemBasedRecommender(model, itemSimilarity);
        
        // Get recommendations for product 102
        List<RecommendedItem> itemRecommendations = itemRecommender.recommend(102, 3);
        System.out.println("\nRecommendations for Product 102:");
        for (RecommendedItem recommendation : itemRecommendations) {
            System.out.println("Product ID: " + recommendation.getItemID() + ", Estimated Rating: " + recommendation.getValue());
        }
    }
}

