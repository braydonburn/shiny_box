package week7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map.Entry;

import config.AppConfig;
import lib.evaluation.RankingMap;
import lib.preprocessing.OutputToFile;

/**
 * Read two top-10 ranking result files (e.g., rank1.txt, and rank2.txt), 
 * calculate average Precision
 */
public class Week7Task2 {

	public static void main(String[] args) throws FileNotFoundException {
		File rankfile1 = new File(AppConfig.DEFAULT_RESOURCE_DIR + "//evaluation//rank1.txt");
		File rankfile2 = new File(AppConfig.DEFAULT_RESOURCE_DIR + "//evaluation//rank2.txt");
		
    	// Outputs console information to specified file, if it doesn't exist create new file
    	FileOutputStream file = new FileOutputStream("src/results/BraydonBurn_wk7_2.txt", false);
    	OutputToFile tee = new OutputToFile(file, System.out);
    	System.setOut(tee);

		System.out.println("Calculate ranking effectiveness of rank1.txt:");
		showRankingEffectiveness(rankfile1);

		System.out.println("Calculate ranking effectiveness of rank2.txt:");
		showRankingEffectiveness(rankfile2);

	}
	
	/**
	 * Shows the ranking effectiveness of a given file.
	 * @param rankfile is the data source of the ranking file.
	 */
	private static void showRankingEffectiveness(File rankfile) {
		final File relevantDoc = new File(AppConfig.DEFAULT_RESOURCE_DIR + "//evaluation//topicdocassign.txt");
		RankingMap<Integer, Integer> ranking = new RankingMap<Integer, Integer>();
		int currentPosition;
		
		try {
			/**
			 * RankingMap may throw the following exceptions:
			 * IOException, FileNotFoundException
			 */
			ranking.loadFromFile(rankfile);
			ranking.identifyRelevantDoc(relevantDoc);
			for (Entry<Integer, Integer> record : ranking.entrySet()) {
				currentPosition = record.getKey();
				System.out.format("At position %2d, precision=%.6f recall=%.6f\n",
						currentPosition,
						ranking.calculateFixedRankPositionPrecision(currentPosition),
						ranking.calculateFixedRankPositionRecall(currentPosition)
						);
			}
			System.out.format("\nIn top-10, average precision = %.6f\n\n\n",
					ranking.getAveragePrecision());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
