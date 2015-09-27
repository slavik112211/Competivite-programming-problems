require 'byebug'

# Algorithm to wrap words in a paragraph gracefully.
# Dynamic programming algorithm by Donald Knuth.

class Wordwrap
  attr_reader :words, :lines_cost_matrix, :line_width, :min_cost, 
              :last_line_start, :word_count, :textlines, :textlines_greedy
  def initialize filename=nil, line_width=nil
    return unless filename
    @words = Array.new
    @word_lengths = Array.new
    @textlines = Array.new
    @textlines_greedy = Array.new
    File.open(filename, 'r').each_line.with_index { |line, index|
      break if line == "\n"
      line.split(" ").each { |word|
        @words << word
        @word_lengths << word.size
      }
    }
    # limiting the length of the line by number of characters allowed
    @line_width = line_width ||= 30
    @word_count=@words.size
  end

  # Building the cost matrix
  def build_cost_matrix
    # extra_space[i][j] is the number of extra spaces at the end 
    # of a line containing words i through j. Extra space may be negative.
    # lines_cost_matrix[i][j] of including a line containing words i through j in the sum we want to minimize.
    @lines_cost_matrix = Array.new(@words.size) { Array.new(@words.size) }
    (0..@word_count-1).each { |i|
      (0..@word_count-1).each { |j|
        # only considering consecutive words (@word[i] has to appear before @word[j] in text)
        next if i>j
        words_length = (i..j).inject(0) { |sum, index| sum+@word_lengths[index] }
        extra_space = @line_width - j + i - words_length
        if extra_space<0 # words don't fit
          @lines_cost_matrix[i][j] = Float::INFINITY
        elsif extra_space>=0 and j==@words.size-1 # last line costs 0
          @lines_cost_matrix[i][j] = 0
        else
          # to emphasize that 1 space costs (1*1*1=1) much less that for ex. 3 spaces (3*3*3=27)
          @lines_cost_matrix[i][j] = extra_space**3
        end
      }
    }
  end

  def calculate_min_cost
    @min_cost = Array.new(@words.size-1)
    @last_line_start = Array.new(@words.size-1)
    (0..@word_count-1).each { |j|
      @min_cost[j]=Float::INFINITY
      (0..j).each { |i|
        previous_min_cost = (i==0) ? 0 : @min_cost[i-1]
        current_cost = previous_min_cost + @lines_cost_matrix[i][j]
        if current_cost < @min_cost[j]
          @min_cost[j] = current_cost
          @last_line_start[j] = i
        end
      }
    }
  end

  # Backtrack through solutions to determine textlines 
  # starting from last word j on the last line.
  def determine_textlines j
    i=last_line_start[j]
    line_number = (i==0) ? 0 : determine_textlines(i-1)+1
    #puts "Line number: #{line_number}; First word: #{i}, last word: #{j}"
    @textlines << [i,j]
    line_number
  end

  def determine_textlines_greedy
    current_line_size=0
    current_line_start=0
    (0..@word_count-1).each { |j|
      if(current_line_size+@word_lengths[j]<=@line_width)
        current_line_size+=@word_lengths[j]+1
      else
        @textlines_greedy << [current_line_start,j-1]
        current_line_start=j
        current_line_size=@word_lengths[j]+1
      end
    }
    @textlines_greedy << [current_line_start,@word_count-1] #add last line
  end

  def print_textlines textlines
    textlines.each{|textline|
      (textline[0]..textline[1]).each{|i| print @words[i]+" " }; puts }
  end

  def print_matrix matrix
    matrix.each {|row| puts row.inspect}
  end

end

def exec
  wordwrap = Wordwrap.new("input2.txt", 50)

  puts "------------------------------"
  puts "Dynamic programming algorithm"
  puts "------------------------------"
  wordwrap.build_cost_matrix
  wordwrap.calculate_min_cost
  wordwrap.determine_textlines(wordwrap.word_count-1)
  wordwrap.print_textlines(wordwrap.textlines)

  puts "------------------------------"
  puts "Greedy algorithm"
  puts "------------------------------"
  wordwrap.determine_textlines_greedy
  wordwrap.print_textlines(wordwrap.textlines_greedy)
end

exec