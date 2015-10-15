class SecretaryProblem
  def initialize size
    @size = size
  end

  # Task A. 
  # Let P(n, k) be the success probability of the strategy of observing the first k diamonds
  # out of n diamonds. Design an algorithm that computes P (n, k) for all k = 1,2, ... , n in O(n) time.
  def build_subsolutions
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

  # Task B.  
  # Note that the answer to (A) is naturally an O(n) time algorithm to compute the optimal
  # k for a given n. But if you repeat this for every n = 1, ... , N, it takes O(n^2) time to
  # compute the optimal k for every n. Try to design an O(N log N) time algorithm or an
  # O(N) time algorithm to compute the optimal k for all n = 1, 2, ... , N.
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