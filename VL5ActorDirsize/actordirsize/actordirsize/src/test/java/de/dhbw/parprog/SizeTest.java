package de.dhbw.parprog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;


@RunWith(JUnit4.class)
public class SizeTest {
    @Test
    public void testResourceSizeIsCorrect() {
        ActorDirSize actorDirSize = new ActorDirSize();
        File testDir = new File(System.getProperty("user.dir"), "src/test/resources/testdir");
        assertThat(testDir.isDirectory()).isTrue();
        DirStats result = actorDirSize.dirStats(testDir);
        assertThat(result.fileCount).isEqualTo(38);
        assertThat(result.totalSize).isEqualTo(3230);
    }
}
