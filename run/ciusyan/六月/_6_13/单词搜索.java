package run.ciusyan.六月._6_13;

/**
 * https://leetcode.cn/problems/word-search/
 */
public class 单词搜索 {
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null) return false;
        char[] chars = word.toCharArray();
        // 尝试从每一个位置开始，去查找 word[i....] 的所有字符能否找到
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                // 说明搜索到了
                if(exist(board, row, col, chars, 0)) return true;
            }
        }

        return false;
    }

    /** 从 (row, col) 开始查找，word[i...] 的字符能否全部找到 */
    private boolean exist(char[][] board, int row, int col, char[] word, int i) {
        // 说明已经找完 word 的所有字符了
        if (i == word.length) return true;

        // 说明越界了都还没找到
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) return false;

        // 说明开头就不相等了，没必要往下搜索了
        if (board[row][col] != word[i]) return false;

        // 来到这里肯定说明 k 字符已经匹配上了，需要去匹配 i + 1 个字符
        // 先记录此字符，然后往四个方向继续搜索
        char temp = board[row][col];
        //将它设置为不存在的字符，这是为了防止往回搜索
        board[row][col] = 0;

        // 分别查看上下左右四个方向，能否匹配到 word[i+1] 个字符
        if (exist(board, row, col + 1, word, i + 1)
        || exist(board, row, col - 1, word, i + 1)
        || exist(board, row + 1, col, word, i + 1)
        || exist(board, row - 1, col, word, i + 1)) {
            return true;
        }

        // 回溯时还原现场
        board[row][col] = temp;

        return false;
    }
}
