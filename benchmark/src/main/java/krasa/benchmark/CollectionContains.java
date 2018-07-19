package krasa.benchmark;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gnu.trove.set.hash.TLinkedHashSet;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CollectionContains {
	public static TestUtils.RandomString randomString = new TestUtils.RandomString(150);

	private String[] array = create(10000, randomString);

	String middle = array[array.length / 2];
	String first = array[0];
	String last = array[array.length - 1];
	String none = randomString.nextString() + randomString.nextString();


	private String[] create(int number, TestUtils.RandomString randomString) {
		String[] strings = new String[number];
		for (int j = 0; j < number; j++) {
			strings[j] = randomString.nextString();
		}
		return strings;
	}

	private ArrayList<String> arrayList = Lists.newArrayList(array);
	private HashSet<String> hashSet = Sets.newHashSet(array);
	private HashSet<String> linkedHashSet = Sets.newLinkedHashSet(arrayList);
	private TLinkedHashSet<String> TlinkedHashSet;

	public CollectionContains() {
		TlinkedHashSet = new TLinkedHashSet<>(array.length);
		TlinkedHashSet.addAll(arrayList);
	}
//	private ImmutableList immutableList = ImmutableList.copyOf(array);
//	private ImmutableSet immutableSet = ImmutableSet.copyOf(array);

//
//	//hashSet
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean hashSetList_contains_first() {
//		return hashSet.contains(first);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean hashSetList_contains_middle() {
//		return hashSet.contains(middle);
//	}
//
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean hashSetList_contains_None() {
//		return hashSet.contains(none);
//	}
//
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean hashSetList_contains_Last() {
//		return hashSet.contains(last);
//	}
//
//
//	//linkedHashSet
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean linkedHashSet_contains_first() {
//		return linkedHashSet.contains(first);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean linkedHashSet_contains_middle() {
//		return linkedHashSet.contains(middle);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean linkedHashSet_contains_None() {
//		return linkedHashSet.contains(none);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean linkedHashSet_contains_Last() {
//		return linkedHashSet.contains(last);
//	}
//
	//TlinkedHashSet
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean TlinkedHashSet_contains_first() {
//		return TlinkedHashSet.contains(first);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean TlinkedHashSet_contains_middle() {
//		return TlinkedHashSet.contains(middle);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean TlinkedHashSet_contains_None() {
//		return TlinkedHashSet.contains(none);
//	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public boolean TlinkedHashSet_contains_Last() {
		return TlinkedHashSet.contains(last);
	}
//
//	//arrayList
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean arrayList_contains_first() {
//		return arrayList.contains(first);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean arrayList_contains_middle() {
//		return arrayList.contains(middle);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean arrayList_contains_None() {
//		return arrayList.contains(none);
//	}
//
//	@Benchmark
//	@BenchmarkMode(Mode.AverageTime)
//	public boolean arrayList_contains_Last() {
//		return arrayList.contains(last);
//	}


	private static final String TEST = ".*CollectionContains.*";

	public static void main(String[] args) throws IOException, RunnerException {
		Options opt = new OptionsBuilder().include(CollectionContains.class.getSimpleName())
			.forks(1)
			.warmupTime(TimeValue.seconds(10))
			.measurementTime(TimeValue.seconds(5))
			.build();

		new Runner(opt).run();

//		Main.main(getArguments(TEST, terations, 5000, 1));
	}

}