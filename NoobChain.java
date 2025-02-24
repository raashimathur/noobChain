import java.util.ArrayList;
public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;

    public static void main(String[] args) {
        // Add blocks to the blockchain
        blockchain.add(new Block("Hi, I'm the first block", "0"));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Yo, I'm the second block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey, I'm the third block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        // Validate the blockchain
        System.out.println("\nBlockchain is Valid: " + isChainValid());

        // Print the blockchain as a formatted string
        System.out.println("\nThe blockchain:");
        System.out.println(getBlockchainAsJson());
    }

    // Validate the blockchain
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            // Compare the stored hash with the calculated hash
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current hashes not equal");
                return false;
            }

            // Compare the previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous hashes not equal");
                return false;
            }

            // Check if the block has been mined
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    // Convert the blockchain to a JSON-like string
    public static String getBlockchainAsJson() {
        StringBuilder json = new StringBuilder("[\n");
        for (Block block : blockchain) {
            json.append("  {\n");
            json.append("    \"hash\": \"").append(block.hash).append("\",\n");
            json.append("    \"previousHash\": \"").append(block.previousHash).append("\",\n");
            json.append("    \"data\": \"").append(block.data).append("\",\n");
            json.append("    \"timeStamp\": ").append(block.timeStamp).append(",\n");
            json.append("    \"nonce\": ").append(block.nonce).append("\n");
            json.append("  },\n");
        }
        if (blockchain.size() > 0) {
            json.delete(json.length() - 2, json.length());
        }
        json.append("\n]");
        return json.toString();
    }
}