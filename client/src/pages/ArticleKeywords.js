import React, { useMemo } from 'react';
import { Alert, Button, ButtonGroup } from 'react-bootstrap';
import { VictoryBar, VictoryChart } from 'victory';
import * as yup from 'yup';
import axios from 'axios';

const ArticleKeywords = () => {
  const [isLink, setIsLink] = React.useState(true);
  const [error, setError] = React.useState(null);
  const [data, setData] = React.useState(null);
  
  const backendServerURL = 'http://medianav.sytes.net:54322';
  
  const sendToAPILink = (input) => {
    return axios({
      method: 'post',
      url: backendServerURL.concat('', '/scrape-article-and-get-wordcloud'),
      headers:{
        'Content-Type': 'application/json',
      },
      data: input
    })
  }
  
  const sendToAPIText = (input) => {
    return axios({
      method: 'post',
      url: backendServerURL.concat('', '/wordcloud'),
      headers:{
        'Content-Type': 'application/json',
      },
      data: input
    })
  }

  const handleSubmit = async (event) => {
    try {
      event.preventDefault();
      setError(null);
      if (isLink) {
        const value = event.target.link.value;
        yup.string().url('Invalid URL').required().validateSync(value);
        const response = await sendToAPILink({ requestForScrappingService: value });
        let res = JSON.parse(response.data.response);
        console.log(res);
        // console.log(res.b64Img);        
        setData(res.b64Img);
      } else {
        const value = event.target.text.value;
        yup.string().required().validateSync(value);
        const response = await sendToAPIText({ requestForDataAnalysisService: value });
        let res = JSON.parse(response.data.response);
        console.log(res);
        // console.log(res.b64Img);        
        setData(res.b64Img);
      }
    } catch (error) {
      setError(error.message);
    }
  };

  const inputs = useMemo(() => {
    setData(null)
    if (isLink) {
      return (
        <>
          <h2 style={{ marginBottom: '20px', color: 'wheat' }}>Website Link</h2>
          <input
            type="text"
            id="link"
            name="link"
            style={{
              padding: '8px',
              width: '40%',
              margin: '10px 0',
              borderRadius: '5px',
              border: '1px solid #ccc',
              color: 'white',
              background: 'transparent',
            }}
          />
        </>
      );
    }

    return (
      <>
        <h2 style={{ marginBottom: '20px', color: 'wheat' }}>
          Text classification
        </h2>
        <textarea
          id="text"
          name="text"
          style={{
            padding: '8px',
            width: '40%',
            margin: '10px 0',
            borderRadius: '5px',
            border: '1px solid #ccc',
            background: 'transparent',
            color: 'white',
            height: '200px',
          }}
        />
      </>
    );
  }, [isLink]);
  
  return (
   <>
    <div className="Form">
      {error ? <Alert variant="danger" onClose={() => setError(null)} dismissible>
        <Alert.Heading>Oh snap! You got an error!</Alert.Heading>
        <p>{error}</p>
      </Alert> : null}
      <form
        style={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          border: '1px solid #ccc',
          padding: '20px',
          borderRadius: '5px',
        }}
        onSubmit={handleSubmit}
      >
        <ButtonGroup aria-label="Basic example" style={{ marginBlock: '1rem' }}>
          <Button
            variant="secondary"
            active={isLink}
            onClick={() => setIsLink(true)}
          >
            Website Link
          </Button>
          <Button
            variant="secondary"
            active={!isLink}
            onClick={() => setIsLink(false)}
          >
            Text classification
          </Button>
        </ButtonGroup>
        {inputs}
        <button
          type="submit"
          style={{
            backgroundColor: 'black',
            color: 'white',
            padding: '8px 20px',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
          }}
        >
          Submit
        </button>
      </form>
    </div>
    {
    data ? (
    <div>
      <center>
        <img src={`data:image/png;base64,${data}`} alt="Word cloud" height="400" width="600"/>
        </center>
    </div>
    ) : null
    }
   </>
  );
}


export default ArticleKeywords