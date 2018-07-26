package reposense.parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import reposense.git.GitDownloader;
import reposense.git.GitDownloaderException;
import reposense.model.Author;
import reposense.model.RepoConfiguration;
import reposense.report.ReportGenerator;

public class RepoConfigurationTest {

    private static final String TEST_REPO_DELTA = "https://github.com/reposense/testrepo-Delta.git";
    private static final Author FIRST_AUTHOR = new Author("lithiumlkid");
    private static final Author SECOND_AUTHOR = new Author("codeeong");
    private static final Author THIRD_AUTHOR = new Author("jordancjq");
    private static final Author FOURTH_AUTHOR = new Author("lohtianwei");


    @Test
    public void configJson_overridesRepoConfig_success()
            throws InvalidLocationException, FileNotFoundException, GitDownloaderException {
        RepoConfiguration expectedConfig = new RepoConfiguration(TEST_REPO_DELTA, "master");

        List<Author> authors = new ArrayList<Author>();
        authors.add(FIRST_AUTHOR);
        authors.add(SECOND_AUTHOR);
        authors.add(THIRD_AUTHOR);
        authors.add(FOURTH_AUTHOR);

        expectedConfig.setAuthorList(authors);

        expectedConfig.setAuthorDisplayName(FIRST_AUTHOR, "Ahm");
        expectedConfig.setAuthorDisplayName(SECOND_AUTHOR, "Cod");
        expectedConfig.setAuthorDisplayName(THIRD_AUTHOR, "Jor");
        expectedConfig.setAuthorDisplayName(FOURTH_AUTHOR, "Loh");

        RepoConfiguration actualConfig = new RepoConfiguration(TEST_REPO_DELTA, "master");
        GitDownloader.downloadRepo(actualConfig);

        ReportGenerator.updateRepoConfig(actualConfig);

        Assert.assertEquals(expectedConfig.getLocation(), actualConfig.getLocation());
        Assert.assertEquals(expectedConfig.getAuthorList().hashCode(), actualConfig.getAuthorList().hashCode());
        Assert.assertEquals(
                expectedConfig.getAuthorDisplayNameMap().hashCode(), actualConfig.getAuthorDisplayNameMap().hashCode());
        Assert.assertEquals(expectedConfig.getAuthorAliasMap().hashCode(), actualConfig.getAuthorAliasMap().hashCode());
    }
}
