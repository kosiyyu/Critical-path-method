import * as THREE from 'three'
import React, {useEffect, useState}  from 'react'
import { ForceGraph2D } from 'react-force-graph'
import {getData} from "./API";

async function fetchData() {
  const data = await getData().catch(console.log)
  console.log(data)
  return data
}

const Graph = () => {

  const [data, setData] = useState({nodes: [], links: []});

  useEffect(() => {
    async function fetchData() {
      const result = await getData().catch(console.log);
      setData(result);
    }

    fetchData();
  }, []);

  const nodes = data.nodes;
  const links = data.links;

  // const nodes = [
  //   {id: 1, name: 'Node1', type: '#1FED75'},
  //   {id: 2, name: 'Node2', type: '#1F84ED'},
  //   {id: 3, name: 'Node3', type: '#1F84ED'},
  //   {id: 4, name: 'Node4', type: '#1F84ED'},
  //   {id: 5, name: 'Node5', type: '#1F84ED'},
  //   {id: 6, name: 'Node6', type: '#1F84ED'},
  //   {id: 7, name: 'Node7', type: '#1F84ED'},
  //   {id: 8, name: 'Node8', type: '#1FED75'},
  // ]

  // const links = [
  //   {source: 1, target: 2, name: 'Link1', type: 'black'},
  //   {source: 1, target: 3, name: 'Link2', type: 'black'},
  //   {source: 2, target: 3, name: 'Link3', type: 'gray'},
  //   {source: 3, target: 4, name: 'Link4', type: 'black'},
  //   {source: 4, target: 6, name: 'Link5', type: 'black'},
  //   {source: 4, target: 5, name: 'Link6', type: 'black'},
  //   {source: 5, target: 6, name: 'Link7', type: 'gray'},
  //   {source: 5, target: 7, name: 'Link8', type: 'black'},
  //   {source: 6, target: 7, name: 'Link9', type: 'black'},
  //   {source: 7, target: 8, name: 'Link10', type: 'black'},
  // ]

  return (
    <ForceGraph2D
      graphData={{ nodes, links }}

      backgroundColor={'#f0f0f0'}
      width={800}
      height={600}

      nodeLabel={'name'}
      nodeColor={node => node.type}
      //nodeColor={() => 'blue'}

      linkLabel={'name'}
      linkDirectionalArrowLength={6}
      linkDirectionalArrowRelPos={1}
      linkCurvature={0}
      linkColor={link => link.type}
    />
  )
}

export default Graph