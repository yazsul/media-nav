import React, { useMemo } from 'react';
import { Alert, Button, ButtonGroup } from 'react-bootstrap';
import { VictoryBar, VictoryChart } from 'victory';
import * as yup from 'yup';


const ArticleTonality = () => {
  const [isLink, setIsLink] = React.useState(true);
  const [error, setError] = React.useState(null);
  const [data, setData] = React.useState(null);
  const backendServerURL = 'http://localhost:8080';
  
  const sendToAPILink = async (data) => {
    const response = fetch(backendServerURL.concat('', '/scrape-article-and-predict-tonality'), {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
  }
  
  const sendToAPIText = async (data) => {
    return fetch(backendServerURL.concat('', '/scrape-article-and-predict-tonality'), {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
  }

  const handleSubmit = async (event) => {
    try {
      event.preventDefault();
      setError(null);
      if (isLink) {
        const value = event.target.link.value;
        yup.string().url('Invalid URL').required().validateSync(value);
        const response = await sendToAPILink({ link: value });
        setData(response);
      } else {
        const value = event.target.text.value;
        yup.string().required().validateSync(value);
        const response = await sendToAPIText({ text: value });
        setData(response);
      }
    } catch (error) {
      setError(error.message);
    }
  };

  const inputs = useMemo(() => {
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


  // Here the data is hardcoded, but you need use the data from the API
  const _data = [
    {class: "center", rate: 0.567},
    {class: "right", rate: 0.378},
    {class: "left", rate: 0.054}
  ];
  
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
    
      data ? null : (
        <div style={{
          position: 'relative',
          zIndex: 1,
          width: '600px',
          color: 'white'
        }}>
          <VictoryChart
            domainPadding={20}
    
          >
            <VictoryBar
              data={data}
              x="class"
              y="rate"
              horizontal
            />
          </VictoryChart>
        </div>
      )
    }
   </>
  );
}

export default ArticleTonality