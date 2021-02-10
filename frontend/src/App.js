import React, { useEffect, useState } from 'react'
import Moment from 'react-moment';

import './Expandable.css'
/**
 * Written by Axel Elmarsson
 */
const App = () => {

    const [builds, setBuilds] = useState([])

    useEffect(_ => {
        fetch("http://localhost:8080/logs")
        .then(res => res.json())
        .then(json => setBuilds(json))
    }, [true])

  return (
    <div className="pretty">
        <h1>Build logs</h1>
        {builds.map(x => <Expandable {...x} />)}
        <Expandable timestamp="2021-02-09T21:34:12.681Z" buildSuccess={true} commitHash="012ec9f1e7442e2777fef039dd2dbdfaef969c10" />
        <Expandable timestamp="2021-02-09T21:34:12.681Z" buildSuccess={false} />
    </div>
  );
}

const Expandable = ({id, timestamp, buildSuccess, commitHash = "", buildResults}) => {

    const [open, toggleOpen] = useState(false)

    return (
    <div className="Expandable" >
        <div className="bar">
            <span className="arrow" onClick={_ => toggleOpen(!open)} style={{cursor: "pointer"}}>
                <i className="fas fa-chevron-down" style={open ? {transform: "rotate(180deg)"} : {}}></i>
            </span>
            <span className="time" onClick={_ => toggleOpen(!open)} style={{cursor: "pointer"}}>
                <Moment format="DD MMM YYYY kk:mm:ss">{timestamp}</Moment>
            </span>
            <span>
                <a
                    href={(`https://github.com/sofrel-group14/ci/commit/${commitHash}`) || "https://github.com/sofrel-group14/ci"}
                    target="_blank"
                    rel="noopener noreferrer"
                    title={commitHash.length != 0 ? (`https://github.com/sofrel-group14/ci/commit/${commitHash}`) : ""}
                >
                    {commitHash.substring(0, 7)}
                </a>
            </span>
            <span>
            {buildSuccess == true ?
                <i className="fas fa-check" style={{color: "green"}} title="Success"></i>
                :
                <i className="fas fa-times" style={{color: "red"}} title="Failure"></i>
            }
            </span>
        </div>
        <div className="content" style={open ? {} : {display: "none"}}>
            {/* TODO: Replace with real output */}
            {`
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------------------< org.decide:decide >--------------------------
[INFO] Building decide 1.0
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ decide ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/andreaskarrby/Documents/KTH/Datateknik/4/soffan (DD2480)/assignments/2/ci/repo/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ decide ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 6 source files to /Users/andreaskarrby/Documents/KTH/Datateknik/4/soffan (DD2480)/assignments/2/ci/repo/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ decide ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/andreaskarrby/Documents/KTH/Datateknik/4/soffan (DD2480)/assignments/2/ci/repo/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ decide ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 17 source files to /Users/andreaskarrby/Documents/KTH/Datateknik/4/soffan (DD2480)/assignments/2/ci/repo/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:3.0.0-M5:test (default-test) @ decide ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running AppTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.067 s - in AppTest
[INFO] Running LIC0Test
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.031 s - in LIC0Test
[INFO] Running LIC10Test
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.012 s - in LIC10Test
[INFO] Running LIC11Test
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 s - in LIC11Test
[INFO] Running LIC12Test
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.01 s - in LIC12Test
[INFO] Running LIC13Test
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 s - in LIC13Test
[INFO] Running LIC14Test
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.012 s - in LIC14Test
[INFO] Running LIC1Test
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.022 s - in LIC1Test
[INFO] Running LIC2Test
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.008 s - in LIC2Test
[INFO] Running LIC3Test
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.023 s - in LIC3Test
[INFO] Running LIC4Test
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.006 s - in LIC4Test
[INFO] Running LIC5Test
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.017 s - in LIC5Test
[INFO] Running LIC6Test
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.009 s - in LIC6Test
[INFO] Running LIC7Test
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.008 s - in LIC7Test
[INFO] Running LIC8Test
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.012 s - in LIC8Test
[INFO] Running LIC9Test
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.014 s - in LIC9Test
[INFO] Running PointTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.018 s - in PointTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 56, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ decide ---
[INFO] Building jar: /Users/andreaskarrby/Documents/KTH/Datateknik/4/soffan (DD2480)/assignments/2/ci/repo/target/decide-1.0.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.002 s
[INFO] Finished at: 2021-02-10T10:29:14+01:00
[INFO] ------------------------------------------------------------------------
            `}
        </div>
    </div>
    )
}

export default App;
