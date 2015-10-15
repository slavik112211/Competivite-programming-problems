class SecretaryProblem
  def initialize size
    @size = size
  end

  def build_subsolutions
    #Pr(k), probability of success with a given k
    @success_probability = Array.new(@size)
    @success_probability[@size-1] = 1.0/@size
    (@size-1).downto(1).each { |k|
      @success_probability[k-1] = 1.0/@size + @success_probability[k]*(k-1)/k
    }
  end

  def print_probabilities_per_k
    puts "Secretary problem size n=#{@size};"
    probabilities = @success_probability.each_with_index.map { |p,i| "k: #{i}, Pr(k): #{p.round(3)}" }
    probabilities.reverse.each {|line| puts line}
  end

  def calculate_optimal_k
    k=0
    @k_optimal = Array.new(@size)
    (3..@size).each{|n|
      k+=1 while(cost(n,k)<cost(n,k+1))
      @k_optimal[n]=k
    }
  end

  def cost(n,k)
    return 0 if k<=0 or k>n-1 
    k*(k..n-1).inject(0) {|sum, i| sum + 1.0/i }
  end

  def print_optimal_k_per_each_n_problem_size
    @k_optimal.each_with_index { |k,n| next if n<3; print "[n=#{n}, k=#{k}], " }
  end

end

def exec
  secretary = SecretaryProblem.new(40)
  secretary.build_subsolutions
  secretary.print_probabilities_per_k
  secretary.calculate_optimal_k
  secretary.print_optimal_k_per_each_n_problem_size
end

exec