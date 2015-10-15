require 'byebug'
I = Float::INFINITY

# http://informatics.mccme.ru/mod/book/view.php?id=488

class LongestIncreasingSubsequence
  attr_reader :subsolutions, :solution
  attr_accessor :sequence
  
  def initialize input
    @sequence = input
  end

  def init_subsolutions_matrix
    @subsolutions = Array.new(@sequence.size+1) { Array.new(@sequence.size+1, nil) }
    (0..@sequence.size).each { |i|
      (1..@sequence.size).each { |j| @subsolutions[i][j] = I }
    }
    (0..@sequence.size).each { |i| @subsolutions[i][0] = -I }
  end

  def build_subsolutions
    init_subsolutions_matrix
    (1..@sequence.size).each { |i|
      (1..i).each { |j|
        if(@subsolutions[i-1][j-1] < @sequence[i-1] and @sequence[i-1] < @subsolutions[i-1][j])
          @subsolutions[i][j] = @sequence[i-1]
        else
          @subsolutions[i][j] = @subsolutions[i-1][j]
        end
      }
    }
  end

  def generate_LIS
    find_LIS_length
    @solution = Array.new
    lis(@sequence.size,@LIS_length)
  end

  # Backtrack through subsolutions to determine LIS
  # i - index of last number of a subsequence.
  # for ex. Complete sequence S = [10,22,9,33,21,50,41,60,80]
  # i = 5; subsequence = [10,22,9,33,21]. i = 7; subsequence = [10,22,9,33,21,50,41]
  # j - LIS length within this subsequence.
  # for ex. i=5; j=3. subsequence = [10,22,9,33,21]; LIS=[10,22,33].
  def lis(i,j)
    sub_j=j-1
    # 1. Since we have to find a position of a number within the original sequence,
    #    this backtracking is only applicable to a sequence where numbers are unique.
    # 2. After accounting for the number at position i,j, we want to deal with a smaller subsequence a(1)..a(j-1). Thus -1.
    # 3. Since subsolutions matrix i=0 row, and j=0 columns do not store values of the original @sequence (they serve as 0-values columns),
    #    we need to adjust index to +1 when adjusting index from @sequence to @subsolutions.
    sub_i=(@sequence.index(@subsolutions[i][j])-1)+1
    lis(sub_i,sub_j) if sub_j>0 
    @solution<<@subsolutions[i][j]
  end

  def find_LIS_length
    j=1; j+=1 while(@subsolutions[@sequence.size][j+1]<I)
    @LIS_length = j
  end

  def print_matrix matrix
    matrix.each {|row| puts row.inspect}
  end

  def print_solution
    puts "Sequence #{@sequence.inspect};"
    puts "LIS length: #{@LIS_length}. LIS: #{@solution.inspect};"
  end
end

def exec
  lis = LongestIncreasingSubsequence.new([10,22,9,33,21,50,41,60,80])
  lis.build_subsolutions
  lis.generate_LIS
  lis.print_solution
end

exec