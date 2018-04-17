//
//  main.cpp
//  graph1
//
//  Created by apple on 2017/12/7.
//  Copyright © 2017年 GJN. All rights reserved.
//

// homework2
// 点光源与方向光源切换 keyboard
// only环境光和漫反射光,and you can add 镜面反射光
// 两种纹理切换 keyboard

// third-party libraries
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <SOIL.h>

// standard C++ libraries
#include <cassert>
#include <iostream>
#include <stdexcept>
#include <cmath>
#include <list>

// tdogl classes
#include "Program.hpp"
#include "Texture.hpp"
#include "Camera.hpp"

/*
 Represents a textured geometry asset
 Contains everything necessary to draw arbitrary geometry with a single texture:
 - shaders
 - a texture
 - a VBO
 - a VAO
 - the parameters to glDrawArrays (drawType, drawStart, drawCount)
*/
struct ModelAsset {
    tdogl::Program* shaders;
    tdogl::Texture* texture;
    GLuint vbo;
    GLuint vao;
    GLenum drawType;
    GLint drawStart;
    GLint drawCount;
    GLfloat shininess;
    glm::vec3 specularColor;
    
    ModelAsset() :
    shaders(NULL),
    texture(NULL),
    vbo(0),
    vao(0),
    drawType(GL_TRIANGLES),
    drawStart(0),
    drawCount(0),
    shininess(0.0f),
    specularColor(1.0f, 1.0f, 1.0f)
    {}
};

/*
 Represents an instance of an `ModelAsset`
 Contains a pointer to the asset, and a model transformation matrix to be used when drawing.
 */
struct ModelInstance {
    ModelAsset* asset;
    glm::mat4 transform;
    
    ModelInstance() :
    asset(NULL),
    transform()
    {}
};

/*
 Represents a point light
 */
struct Light {
    glm::vec3 position;
    glm::vec3 intensities; //a.k.a. the color of the light
    float attenuation;
    float ambientCoefficient;
};

// constants
const glm::vec2 SCREEN_SIZE(800, 600);

// globals
GLFWwindow* gWindow = NULL;
double gScrollY = 0.0;
tdogl::Camera gCamera;
ModelAsset Acude, Acude2, Acude3, Asphere, Acolumn, Acone, Abottom, Aleft, Aright, Atop, Afront, Aback, Alight;
//, Asky; //model
std::list<ModelInstance> gInstances;
GLfloat gDegreesRotated = 0.0f;
Light gLight;

// returns a new tdogl::Program created from the given vertex and fragment shader filenames
static tdogl::Program* LoadShaders(const char* vpath, const char* fpath) {
    std::vector<tdogl::Shader> shaders;
    shaders.push_back(tdogl::Shader::shaderFromFile(vpath, GL_VERTEX_SHADER));
    shaders.push_back(tdogl::Shader::shaderFromFile(fpath, GL_FRAGMENT_SHADER));
    return new tdogl::Program(shaders);
}

// returns a new tdogl::Texture created from the given filename
static tdogl::Texture* LoadTexture(const char* filepath) {
    tdogl::Bitmap bmp = tdogl::Bitmap::bitmapFromFile(filepath);
    bmp.flipVertically();
    return new tdogl::Texture(bmp);
}

// initialises the Acude global
static void LoadWoodenCrateAsset() {
    // set all the elements of Acude
    // you can set a variable
    Acude.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/vertex-shader.txt","/Users/apple/Documents/graph1/graph1/resources/fragment-shader.txt");
    Acude.drawType = GL_TRIANGLES;
    Acude.drawStart = 0;
    Acude.drawCount = 6*2*3;
    // you can set a variable too xiangduilujing?
    Acude.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/haimian.png");
    Acude.shininess = 10.0; // change
    Acude.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Acude.vbo);
    glGenVertexArrays(1, &Acude.vao);
    
    // bind the VAO
    glBindVertexArray(Acude.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Acude.vbo);
    
    // Make a cube out of triangles (two triangles per side)
    GLfloat vertexData[] = {
        //  X     Y     Z       U     V          Normal
        // bottom
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        
        // top
        -1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        
        // front
        -1.0f,-1.0f, 1.0f,   1.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        1.0f,-1.0f, 1.0f,   0.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        1.0f,-1.0f, 1.0f,   0.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        
        // back
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        -1.0f, 1.0f,-1.0f,   0.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        -1.0f, 1.0f,-1.0f,   0.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        
        // left
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        
        // right
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   1.0f, 0.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   1.0f, 0.0f, 0.0f
    };
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Acude.shaders->attrib("vert"));
    glVertexAttribPointer(Acude.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 8*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Acude.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Acude.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  8*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Acude.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Acude.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  8*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}


// convenience function that returns a translation matrix
glm::mat4 translate(GLfloat x, GLfloat y, GLfloat z) {
    return glm::translate(glm::mat4(), glm::vec3(x,y,z));
}


// convenience function that returns a scaling matrix
glm::mat4 scale(GLfloat x, GLfloat y, GLfloat z) {
    return glm::scale(glm::mat4(), glm::vec3(x,y,z));
}
//create all the `instance` structs for the 3D scene, and add them to `gInstances`
static void CreateInstances() {
    
    //write a word "hello"
    /*ModelInstance dot;
    dot.asset = &Acude;
    dot.transform = glm::mat4();
    gInstances.push_back(dot);*/
    
    ModelInstance head;
    head.asset = &Acude;
    head.transform = translate(-15,0,-5) * scale(3,3,1.5);
    gInstances.push_back(head);
    
    ModelInstance leftleg;
    leftleg.asset = &Acude;
    leftleg.transform = translate(-16.5,-8,-5) * scale(0.5,2.5,0.5);
    gInstances.push_back(leftleg);
    
    ModelInstance rightleg;
    rightleg.asset = &Acude;
    rightleg.transform = translate(-13.5,-8,-5) * scale(0.5,2.5,0.5);
    gInstances.push_back(rightleg);
    
    ModelInstance leftarm;
    leftarm.asset = &Acude;
    leftarm.transform = translate(-20,-1.5,-5) * scale(2,0.5,0.5);
    gInstances.push_back(leftarm);
    
    ModelInstance rightarm;
    rightarm.asset = &Acude;
    rightarm.transform = translate(-10,-1.5,-5) * scale(2,0.5,0.5);
    gInstances.push_back(rightarm);
    
    ModelInstance nose;
    nose.asset = &Acude;
    nose.transform = translate(-15,-0.1,-3.5) * scale(0.2,0.3,1.2);
    gInstances.push_back(nose);
    
    ModelInstance mouse;
    mouse.asset = &Acude;
    mouse.transform = translate(-15,-0.6,-3.5) * scale(1.5,0.2,0.5);
    gInstances.push_back(mouse);
    
    ModelInstance leftface;
    leftface.asset = &Acude;
    leftface.transform = translate(-16.6,-0.5,-3.5) * scale(0.25,0.3,0.5);
    gInstances.push_back(leftface);
    
    ModelInstance rightface;
    rightface.asset = &Acude;
    rightface.transform = translate(-13.4,-0.5,-3.5) * scale(0.25,0.3,0.5);
    gInstances.push_back(rightface);
    
    ModelInstance righthand;
    righthand.asset = &Acude;
    righthand.transform = translate(-8,-1.5,-5) * scale(0.8,0.8,0.5);
    gInstances.push_back(righthand);
    
    ModelInstance lefthand;
    lefthand.asset = &Acude;
    lefthand.transform = translate(-22,-1.5,-5) * scale(0.8,0.8,0.5);
    gInstances.push_back(lefthand);
}
// 2
static void LoadWoodenCrateAsset2() {
    // set all the elements of Acude
    // you can set a variable
    Acude2.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/vertex-shader.txt","/Users/apple/Documents/graph1/graph1/resources/fragment-shader.txt");
    Acude2.drawType = GL_TRIANGLES;
    Acude2.drawStart = 0;
    Acude2.drawCount = 6*2*3;
    // you can set a variable too xiangduilujing?
    Acude2.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/white.png");
    Acude2.shininess = 10.0; // change
    Acude2.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Acude2.vbo);
    glGenVertexArrays(1, &Acude2.vao);
    
    // bind the VAO
    glBindVertexArray(Acude2.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Acude2.vbo);
    
    // Make a cube out of triangles (two triangles per side)
    GLfloat vertexData[] = {
        //  X     Y     Z       U     V          Normal
        // bottom
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        
        // top
        -1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        
        // front
        -1.0f,-1.0f, 1.0f,   1.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        1.0f,-1.0f, 1.0f,   0.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        1.0f,-1.0f, 1.0f,   0.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        
        // back
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        -1.0f, 1.0f,-1.0f,   0.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        -1.0f, 1.0f,-1.0f,   0.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        
        // left
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        
        // right
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   1.0f, 0.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   1.0f, 0.0f, 0.0f
    };
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Acude2.shaders->attrib("vert"));
    glVertexAttribPointer(Acude2.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 8*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Acude2.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Acude2.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  8*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Acude2.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Acude2.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  8*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
static void CreateInstances2() {
    
    ModelInstance leftteeth;
    leftteeth.asset = &Acude2;
    leftteeth.transform = translate(-15.3,-1.1,-3.5) * scale(0.2,0.3,0.3);
    gInstances.push_back(leftteeth);
    
    ModelInstance rightteeth;
    rightteeth.asset = &Acude2;
    rightteeth.transform = translate(-14.7,-1.1,-3.5) * scale(0.2,0.3,0.3);
    gInstances.push_back(rightteeth);
    
    ModelInstance body;
    body.asset = &Acude2;
    body.transform = translate(-15,-4,-5) * scale(3,1,1.5);
    gInstances.push_back(body);
}
// 3
static void LoadWoodenCrateAsset3() {
    // set all the elements of Acude
    // you can set a variable
    Acude3.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/vertex-shader.txt","/Users/apple/Documents/graph1/graph1/resources/fragment-shader.txt");
    Acude3.drawType = GL_TRIANGLES;
    Acude3.drawStart = 0;
    Acude3.drawCount = 6*2*3;
    // you can set a variable too xiangduilujing?
    Acude3.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/black.png");
    Acude3.shininess = 10.0; // change
    Acude3.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Acude3.vbo);
    glGenVertexArrays(1, &Acude3.vao);
    
    // bind the VAO
    glBindVertexArray(Acude3.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Acude3.vbo);
    
    // Make a cube out of triangles (two triangles per side)
    GLfloat vertexData[] = {
        //  X     Y     Z       U     V          Normal
        // bottom
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        
        // top
        -1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        
        // front
        -1.0f,-1.0f, 1.0f,   1.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        1.0f,-1.0f, 1.0f,   0.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        1.0f,-1.0f, 1.0f,   0.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        
        // back
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        -1.0f, 1.0f,-1.0f,   0.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        -1.0f, 1.0f,-1.0f,   0.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        
        // left
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        
        // right
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   1.0f, 0.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   1.0f, 0.0f, 0.0f
    };
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Acude3.shaders->attrib("vert"));
    glVertexAttribPointer(Acude3.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 8*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Acude3.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Acude3.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  8*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Acude3.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Acude3.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  8*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
static void CreateInstances3() {
    
    ModelInstance tie;
    tie.asset = &Acude3;
    tie.transform = translate(-15,-3.7,-3) * scale(0.1,0.5,0.2);
    gInstances.push_back(tie);
    
    ModelInstance tie2;
    tie2.asset = &Acude3;
    tie2.transform = translate(-15,-3.2,-3) * scale(0.3,0.3,0.2);
    gInstances.push_back(tie2);
    
    ModelInstance cloth;
    cloth.asset = &Acude3;
    cloth.transform = translate(-15,-5.5,-5) * scale(3,0.5,1.5);
    gInstances.push_back(cloth);
    
    ModelInstance leftfoot;
    leftfoot.asset = &Acude3;
    leftfoot.transform = translate(-17,-10.5,-5) * scale(1.5,0.5,1);
    gInstances.push_back(leftfoot);
    
    ModelInstance rightfoot;
    rightfoot.asset = &Acude3;
    rightfoot.transform = translate(-13,-10.5,-5) * scale(1.5,0.5,1);
    gInstances.push_back(rightfoot);
}
//===========================add a sphere=================================
// initialises the Asphere global
static void LoadSphere() {
    // set all the elements of Asphere
    // you can set a variable
    Asphere.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/vertex-shader.txt","/Users/apple/Documents/graph1/graph1/resources/fragment-shader.txt");
    Asphere.drawType = GL_TRIANGLES;
    Asphere.drawStart = 0;
    Asphere.drawCount = 10000;
    // you can set a variable too xiangduilujing?
    Asphere.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/blue.png");
    Asphere.shininess = 10.0; // change
    Asphere.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Asphere.vbo);
    glGenVertexArrays(1, &Asphere.vao);
    
    // bind the VAO
    glBindVertexArray(Asphere.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Asphere.vbo);
    
    // Make a sphere out of triangles
    GLfloat vertexData[100000] = {};
    
    GLfloat xx = -16.5, yy = 3, zz = 12, radius = 0.5, M = 50, N = 50;
    float step_z = M_PI / M; // z方向每次步进的角度
    float step_xy = 2 * M_PI / N; // x,y平面每次步进的角度
    float x[4],y[4],z[4]; // 用来存坐标
    float angle_z = 0.0; // 起始角度
    float angle_xy = 0.0;
    int i = 0, j = 0, num = 0;
    
    for(i=0; i<M; i++)
    {
        angle_z = i * step_z; // 每次步进step_z
        
        for(j=0; j<N; j++)
        {
            angle_xy = j * step_xy; // 每次步进step_xy
            // 一层一层的画出来
            x[0] = radius * sin(angle_z) * cos(angle_xy); // 第一个小平面的第一个顶点坐标
            y[0] = radius * sin(angle_z) * sin(angle_xy);
            z[0] = radius * cos(angle_z);
            
            x[1] = radius * sin(angle_z + step_z) * cos(angle_xy); // 第一个小平面的第二个顶点坐标，下面类似
            y[1] = radius * sin(angle_z + step_z) * sin(angle_xy);
            z[1] = radius * cos(angle_z + step_z);
            
            x[2] = radius * sin(angle_z + step_z) * cos(angle_xy + step_xy);
            y[2] = radius * sin(angle_z + step_z) * sin(angle_xy + step_xy);
            z[2] = radius * cos(angle_z + step_z);
            
            x[3] = radius * sin(angle_z) * cos(angle_xy + step_xy);
            y[3] = radius * sin(angle_z) * sin(angle_xy + step_xy);
            z[3] = radius * cos(angle_z);
            // 至此得到一个平面的4个顶点
            for(int k = 0; k < 4; k++)
            {
                vertexData[num] = xx + x[k];
                vertexData[num+1] = yy + y[k];
                vertexData[num+2] = zz + z[k];
                num = num + 3;
                //std::cout << num << std::endl;
            }
        }
        //循环画出这一层的平面，组成一个环
    }
    //z轴++，画出剩余层
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Asphere.shaders->attrib("vert"));
    glVertexAttribPointer(Asphere.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 3*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Asphere.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Asphere.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  3*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Asphere.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Asphere.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  3*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}

//create all the `instance` structs for the 3D scene, and add them to `gInstances`
static void SphereInstances() {
    
    ModelInstance righteye;
    righteye.asset = &Asphere;
    righteye.transform = translate(-5.5,-0.5,-8.8) * scale(0.5,0.5,0.5);
    gInstances.push_back(righteye);
    
    ModelInstance lefteye;
    lefteye.asset = &Asphere;
    lefteye.transform = translate(-8,-0.5,-8.8) * scale(0.5,0.5,0.5);
    gInstances.push_back(lefteye);

    /*
    ModelInstance house;
    house.asset = &Asphere;
    house.transform = translate(55,-21,-60) * scale(5,5,5);
    gInstances.push_back(house);*/
    
}
//===========================add a sphere=================================

//===========================add a column=================================
// initialises the Acolumn global
static void LoadColumn() {
    // set all the elements of Acolumn
    // you can set a variable
    Acolumn.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/vertex-shader.txt","/Users/apple/Documents/graph1/graph1/resources/fragment-shader.txt");
    Acolumn.drawType = GL_TRIANGLES;
    Acolumn.drawStart = 0;
    Acolumn.drawCount = 1658; // v number
    // you can set a variable too xiangduilujing?
    Acolumn.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/white.png");
    Acolumn.shininess = 10.0; // change
    Acolumn.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Acolumn.vbo);
    glGenVertexArrays(1, &Acolumn.vao);
    
    // bind the VAO
    glBindVertexArray(Acolumn.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Acolumn.vbo);
    
    // Make a column out of triangles
    GLfloat vertexData[10000] = {};
    GLfloat x = -13.5, y = 3, z = 12; //position
    
    int num = 0;
    for(GLfloat i = 0; i < 2.0f; i += 0.2)
        for (GLfloat j = 0; j < 2.0f * M_PI; j += 0.2)
        {
            vertexData[num] = x+0.4*cos(j);
            vertexData[num+1] = y+0.4*sin(j);
            vertexData[num+2] = z+0.4*i;
            
            vertexData[num+3] = x+0.4*cos(j+1);
            vertexData[num+4] = y+0.4*sin(j+1);
            vertexData[num+5] = z+0.4*i;
            
            vertexData[num+6] = x+0.4*cos(j+1);
            vertexData[num+7] = y+0.4*sin(j+1);
            vertexData[num+8] = z+0.4*(i+1);
            
            vertexData[num+9] = x+0.4*cos(j);
            vertexData[num+10] = y+0.4*sin(j);
            vertexData[num+11] = z+0.4*(i+1);
            
            num = num + 12;
        }
    //std::cout << num << std::endl;
    for (GLfloat k = 0; k < 2.0f * M_PI; k += 0.05)
    {
        vertexData[num] = x;
        vertexData[num+1] = y;
        vertexData[num+2] = z;
        vertexData[num+3] = x+0.4*cos(k);
        vertexData[num+4] = y+0.4*sin(k);
        vertexData[num+5] = z;
        vertexData[num+6] = x+0.4*cos(k+0.05);
        vertexData[num+7] = y+0.4*sin(k+0.05);
        vertexData[num+8] = z;
        num = num + 9;
    }
    //std::cout << num << std::endl;
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Acolumn.shaders->attrib("vert"));
    glVertexAttribPointer(Acolumn.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 3*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Acolumn.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Acolumn.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  3*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Acolumn.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Acolumn.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  3*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}

//create all the `instance` structs for the 3D scene, and add them to `gInstances`
static void ColumnInstances() {
    
    ModelInstance lefteye;
    lefteye.asset = &Acolumn;
    lefteye.transform = translate(24.3,-8,-9.3) * scale(3,3,0.5);
    gInstances.push_back(lefteye);
    
    ModelInstance righteye;
    righteye.asset = &Acolumn;
    righteye.transform = translate(26.7,-8,-9.3) * scale(3,3,0.5);
    gInstances.push_back(righteye);
    
}
//===========================add a column=================================

//===========================add a cone=================================
// initialises the Acone global
static void LoadCone() {
    // set all the elements of Acone
    // you can set a variable
    Acone.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/vertex-shader.txt","/Users/apple/Documents/graph1/graph1/resources/fragment-shader.txt");
    Acone.drawType = GL_TRIANGLES;
    Acone.drawStart = 0;
    Acone.drawCount = 264; // v number
    // you can set a variable too xiangduilujing?
    Acone.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/red.png");
    Acone.shininess = 10.0; // change
    Acone.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Acone.vbo);
    glGenVertexArrays(1, &Acone.vao);
    
    // bind the VAO
    glBindVertexArray(Acone.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Acone.vbo);
    
    // Make a cone out of triangles
    GLfloat vertexData[1000] = {};
    
    GLfloat angle, x, z, y;
    
    int i = 0;

    for (angle = 0.0f; angle < (4.0f * M_PI + M_PI / 8.0f); angle += (M_PI / 8.0f)) {
        // 计算下一个顶点的位置
        x = -15, z = -5;
        x += 0.6f * sin(angle);
        z += 0.6f * cos(angle);
        // 指定三角形扇的下一个顶点
        
        vertexData[i] = x;
        vertexData[i+1] = 3;
        vertexData[i+2] = z;
        
        vertexData[i+3] = -15;
        vertexData[i+4] = 3;
        vertexData[i+5] = -5;
        
        vertexData[i+6]= -15;
        vertexData[i+7]= 5.5;
        vertexData[i+8]= -5;
        
        vertexData[i+9] = x;
        vertexData[i+10] = 3;
        vertexData[i+11] = z;
        
        i = i + 12;
    }
    //std::cout << i << std::endl;

    for (angle = 0.0f; angle < (4.0f * M_PI + M_PI / 8.0f); angle += (M_PI / 8.0f)) {
        // 计算下一个顶点的位置
        x = -15, z = -5;
        x += 0.6f * sin(angle);
        z += 0.6f * cos(angle);
        // 指定三角形扇的下一个顶点
        vertexData[i] = x;
        vertexData[i+1] = 3;
        vertexData[i+2] = z;
        
        vertexData[i+3] = -15;
        vertexData[i+4] = 3;
        vertexData[i+5] = -5;
        
        vertexData[i+6] = -15;
        vertexData[i+7] = 3;
        vertexData[i+8] = -5;
        
        vertexData[i+9] = x;
        vertexData[i+10] = 3;
        vertexData[i+11] = z;
        
        i = i + 12;
    }
    //std::cout << i << std::endl;
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Acone.shaders->attrib("vert"));
    glVertexAttribPointer(Acone.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 3*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Acone.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Acone.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  3*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Acone.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Acone.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  3*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}

//create all the `instance` structs for the 3D scene, and add them to `gInstances`
static void ConeInstances() {
    
    ModelInstance dot;
    dot.asset = &Acone;
    dot.transform = glm::mat4();
    gInstances.push_back(dot);
}
//===========================add a cone=================================

//============================ skybox ==================================
// 地面
static void LoadFloor() {
    
    // set all the elements of Asphere
    // you can set a variable
    Abottom.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/bumpmap-vshader.txt","/Users/apple/Documents/graph1/graph1/resources/bumpmap-fshader.txt");
    Abottom.drawType = GL_TRIANGLES;
    Abottom.drawStart = 0;
    Abottom.drawCount = 6;
    // you can set a variable too xiangduilujing?
    Abottom.texture = LoadTexture("/Users/apple/Downloads/图形/skyboxs/半空中_textures/半空中_DN.tga");
    Abottom.shininess = 10.0; // change
    Abottom.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Abottom.vbo);
    glGenVertexArrays(1, &Abottom.vao);
    
    // bind the VAO
    glBindVertexArray(Abottom.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Abottom.vbo);
    
    // 地面
    GLfloat vertexData[] = {
         20.0f,  0,  20.0f,  1.0f, 0.0f,
        -20.0f, -0,  20.0f,  0.0f, 0.0f,
        -20.0f, -0, -20.0f,  0.0f, 1.0f,
        
         20.0f,  -0,  20.0f,  1.0f, 0.0f,
        -20.0f,  -0,  -20.0f, 0.0f, 1.0f,
         20.0f,  -0, -20.0f,  1.0f, 1.0f
    };
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Abottom.shaders->attrib("vert"));
    glVertexAttribPointer(Abottom.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 5*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Abottom.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Abottom.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Abottom.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Abottom.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
static void floorInstances() {
    
    ModelInstance floor;
    floor.asset = &Abottom;
    floor.transform = translate(-15,-60, 30) * scale(3.5,0,3.5);
    gInstances.push_back(floor);
}
//left
static void LoadLeft() {
    // set all the elements of Asphere
    // you can set a variable
    Aleft.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/bumpmap-vshader.txt","/Users/apple/Documents/graph1/graph1/resources/bumpmap-fshader.txt");
    Aleft.drawType = GL_TRIANGLES;
    Aleft.drawStart = 0;
    Aleft.drawCount = 6;
    // you can set a variable too xiangduilujing?
    Aleft.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/left.png");
    Aleft.shininess = 10.0; // change
    Aleft.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Aleft.vbo);
    glGenVertexArrays(1, &Aleft.vao);
    
    // bind the VAO
    glBindVertexArray(Aleft.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Aleft.vbo);
    
    // LEFT
    GLfloat vertexData[] = {
        -20.0f, -20.0,  20.0f,  0.0f, 1.0f,
        -20.0f,  20.0, -20.0f,  1.0f, 0.0f,
        -20.0f, -20.0, -20.0f,  0.0f, 0.0f,
        
        -20.0f, -20.0,  20.0f,  0.0f, 1.0f,
        -20.0f,  20.0,  20.0f,  1.0f, 1.0f,
        -20.0f,  20.0, -20.0f,  1.0f, 0.0f
    };
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Aleft.shaders->attrib("vert"));
    glVertexAttribPointer(Aleft.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 5*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Aleft.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Aleft.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Aleft.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Aleft.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
static void leftInstances() {
    
    ModelInstance left;
    left.asset = &Aleft;
    left.transform = translate(-65,-20, 10) * scale(0,4,4);
    gInstances.push_back(left);
}
//right
static void LoadRight() {
    
    // set all the elements of Asphere
    // you can set a variable
    Aright.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/bumpmap-vshader.txt","/Users/apple/Documents/graph1/graph1/resources/bumpmap-fshader.txt");
    Aright.drawType = GL_TRIANGLES;
    Aright.drawStart = 0;
    Aright.drawCount = 6;
    // you can set a variable too xiangduilujing?
    Aright.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/right.png");
    Aright.shininess = 10.0; // change
    Aright.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Aright.vbo);
    glGenVertexArrays(1, &Aright.vao);
    
    // bind the VAO
    glBindVertexArray(Aright.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Aright.vbo);

    // RIGHT
    GLfloat vertexData[] = {
        20.0f, -20.0,  20.0f,  1.0f, 1.0f,
        20.0f, -20.0, -20.0f,  1.0f, 0.0f,
        20.0f,  20.0, -20.0f,  0.0f, 0.0f,
        
        20.0f, -20.0,  20.0f,  1.0f, 1.0f,
        20.0f,  20.0, -20.0f,  0.0f, 0.0f,
        20.0f,  20.0,  20.0f,  0.0f, 1.0f
    };
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Aright.shaders->attrib("vert"));
    glVertexAttribPointer(Aright.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 5*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Aright.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Aright.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Aright.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Aright.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
static void rightInstances() {
    
    ModelInstance right;
    right.asset = &Aright;
    right.transform = translate(45,-20, 10) * scale(0,4,4);
    gInstances.push_back(right);
}
//top
static void LoadTop() {
    
    // set all the elements of Asphere
    // you can set a variable
    Atop.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/bumpmap-vshader.txt","/Users/apple/Documents/graph1/graph1/resources/bumpmap-fshader.txt");
    Atop.drawType = GL_TRIANGLES;
    Atop.drawStart = 0;
    Atop.drawCount = 6;
    // you can set a variable too xiangduilujing?
    Atop.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/up.png");
    Atop.shininess = 10.0; // change
    Atop.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Atop.vbo);
    glGenVertexArrays(1, &Atop.vao);
    
    // bind the VAO
    glBindVertexArray(Atop.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Atop.vbo);
    
    // TOP
    GLfloat vertexData[] = {
        -20.0f, 20.0, -20.0f,  0.0f, 0.0f,
        -20.0f, 20.0,  20.0f,  0.0f, 1.0f,
         20.0f, 20.0, -20.0f,  1.0f, 0.0f,
        
         20.0f, 20.0, -20.0f,  1.0f, 0.0f,
        -20.0f, 20.0,  20.0f,  0.0f, 1.0f,
         20.0f, 20.0,  20.0f,  1.0f, 1.0f
    };
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Atop.shaders->attrib("vert"));
    glVertexAttribPointer(Atop.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 5*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Atop.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Atop.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Atop.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Atop.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
static void topInstances() {
    
    ModelInstance top;
    top.asset = &Atop;
    top.transform = translate(-15, 63, 10) * scale(4,0,4);
    gInstances.push_back(top);
}
//front
static void LoadFront() {
    
    // set all the elements of Asphere
    // you can set a variable
    Afront.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/bumpmap-vshader.txt","/Users/apple/Documents/graph1/graph1/resources/bumpmap-fshader.txt");
    Afront.drawType = GL_TRIANGLES;
    Afront.drawStart = 0;
    Afront.drawCount = 6;
    // you can set a variable too xiangduilujing?
    Afront.texture = LoadTexture("/Users/apple/Downloads/图形/skyboxs/半空中_textures/半空中_FR.tga");
    Afront.shininess = 10.0; // change
    Afront.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Afront.vbo);
    glGenVertexArrays(1, &Afront.vao);
    
    // bind the VAO
    glBindVertexArray(Afront.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Afront.vbo);
    
    // FRONT
    GLfloat vertexData[] = {
        -20.0f, -20.0,  20.0f,  1.0f, 0.0f,
         20.0f, -20.0,  20.0f,  0.0f, 0.0f,
        -20.0f,  20.0,  20.0f,  1.0f, 1.0f,
        
         20.0f, -20.0,  20.0f,  0.0f, 0.0f,
         20.0f,  20.0,  20.0f,  0.0f, 1.0f,
        -20.0f,  20.0,  20.0f,  1.0f, 1.0f
    };
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Afront.shaders->attrib("vert"));
    glVertexAttribPointer(Afront.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 5*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Afront.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Afront.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Afront.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Afront.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
static void frontInstances() {
    
    ModelInstance front;
    front.asset = &Afront;
    front.transform = translate(-15,-20, 80) * scale(4,4,0);
    gInstances.push_back(front);
}
//back
static void LoadBack() {
    
    // set all the elements of Asphere
    // you can set a variable
    Aback.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/bumpmap-vshader.txt","/Users/apple/Documents/graph1/graph1/resources/bumpmap-fshader.txt");
    Aback.drawType = GL_TRIANGLES;
    Aback.drawStart = 0;
    Aback.drawCount = 6;
    // you can set a variable too xiangduilujing?
    Aback.texture = LoadTexture("/Users/apple/Downloads/图形/skyboxs/半空中_textures/半空中_FR.tga");
    Aback.shininess = 10.0; // change
    Aback.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Aback.vbo);
    glGenVertexArrays(1, &Aback.vao);
    
    // bind the VAO
    glBindVertexArray(Aback.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Aback.vbo);
    
    // BACK
    GLfloat vertexData[] = {
        -20.0f, -20.0, -20.0f,  0.0f, 0.0f,
        -20.0f,  20.0, -20.0f,  0.0f, 1.0f,
         20.0f, -20.0, -20.0f,  1.0f, 0.0f,
        
         20.0f, -20.0, -20.0f,  1.0f, 0.0f,
        -20.0f,  20.0, -20.0f,  0.0f, 1.0f,
         20.0f,  20.0, -20.0f,  1.0f, 1.0f
    };
    
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Aback.shaders->attrib("vert"));
    glVertexAttribPointer(Aback.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 5*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Aback.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Aback.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Aback.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Aback.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  5*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
static void backInstances() {
    
    ModelInstance back;
    back.asset = &Aback;
    back.transform = translate(-15,-20,-30) * scale(4,4,0);
    gInstances.push_back(back);
}
//============================ skybox ==================================
//============
// initialises the Acude global
static void LoadLight() {
    // set all the elements of Acude
    // you can set a variable
    Alight.shaders = LoadShaders("/Users/apple/Documents/graph1/graph1/resources/vertex-shader.txt","/Users/apple/Documents/graph1/graph1/resources/fragment-shader2.txt");
    Alight.drawType = GL_TRIANGLES;
    Alight.drawStart = 0;
    Alight.drawCount = 6*2*3;
    // you can set a variable too xiangduilujing?
    Alight.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/lamp.png");
    Alight.shininess = 10.0; // change
    Alight.specularColor = glm::vec3(1.0f, 1.0f, 1.0f); // white light
    glGenBuffers(1, &Alight.vbo);
    glGenVertexArrays(1, &Alight.vao);
    
    // bind the VAO
    glBindVertexArray(Alight.vao);
    
    // bind the VBO
    glBindBuffer(GL_ARRAY_BUFFER, Alight.vbo);
    
    // Make a cube out of triangles (two triangles per side)
    GLfloat vertexData[] = {
        //  X     Y     Z       U     V          Normal
        // bottom
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, -1.0f, 0.0f,
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   0.0f, -1.0f, 0.0f,
        
        // top
        -1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 1.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 1.0f, 0.0f,
        
        // front
        -1.0f,-1.0f, 1.0f,   1.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        1.0f,-1.0f, 1.0f,   0.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        1.0f,-1.0f, 1.0f,   0.0f, 0.0f,   0.0f, 0.0f, 1.0f,
        1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   0.0f, 0.0f, 1.0f,
        
        // back
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        -1.0f, 1.0f,-1.0f,   0.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   0.0f, 0.0f, -1.0f,
        -1.0f, 1.0f,-1.0f,   0.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        1.0f, 1.0f,-1.0f,   1.0f, 1.0f,   0.0f, 0.0f, -1.0f,
        
        // left
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f,-1.0f,-1.0f,   0.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,   0.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f, 1.0f,   1.0f, 1.0f,   -1.0f, 0.0f, 0.0f,
        -1.0f, 1.0f,-1.0f,   1.0f, 0.0f,   -1.0f, 0.0f, 0.0f,
        
        // right
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   1.0f, 0.0f, 0.0f,
        1.0f,-1.0f,-1.0f,   1.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f,-1.0f, 1.0f,   1.0f, 1.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f,-1.0f,   0.0f, 0.0f,   1.0f, 0.0f, 0.0f,
        1.0f, 1.0f, 1.0f,   0.0f, 1.0f,   1.0f, 0.0f, 0.0f
    };
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), vertexData, GL_STATIC_DRAW);
    
    // connect the xyz to the "vert" attribute of the vertex shader
    glEnableVertexAttribArray(Alight.shaders->attrib("vert"));
    glVertexAttribPointer(Alight.shaders->attrib("vert"), 3, GL_FLOAT, GL_FALSE, 8*sizeof(GLfloat), NULL);
    
    // connect the uv coords to the "vertTexCoord" attribute of the vertex shader
    glEnableVertexAttribArray(Alight.shaders->attrib("vertTexCoord"));
    glVertexAttribPointer(Alight.shaders->attrib("vertTexCoord"), 2, GL_FLOAT, GL_TRUE,  8*sizeof(GLfloat), (const GLvoid*)(3 * sizeof(GLfloat)));
    
    // connect the normal to the "vertNormal" attribute of the vertex shader
    glEnableVertexAttribArray(Alight.shaders->attrib("vertNormal"));
    glVertexAttribPointer(Alight.shaders->attrib("vertNormal"), 3, GL_FLOAT, GL_TRUE,  8*sizeof(GLfloat), (const GLvoid*)(5 * sizeof(GLfloat)));
    
    // unbind the VAO
    glBindVertexArray(0);
}
//create all the `instance` structs for the 3D scene, and add them to `gInstances`
static void lightInstances() {
    ModelInstance light;
    light.asset = &Alight;
    light.transform = translate(-15,5.7,-5) * scale(0.3,0.3,0.3);
    gInstances.push_back(light);
}
//============

//renders a single `ModelInstance`
static void RenderInstance(const ModelInstance& inst) {
    ModelAsset* asset = inst.asset;
    tdogl::Program* shaders = asset->shaders;
    
    //bind the shaders
    shaders->use();
    
    //set the shader uniforms
    shaders->setUniform("camera", gCamera.matrix());
    shaders->setUniform("model", inst.transform);
    shaders->setUniform("materialTex", 0); //set to 0 because the texture will be bound to GL_TEXTURE0
    shaders->setUniform("materialShininess", asset->shininess);
    shaders->setUniform("materialSpecularColor", asset->specularColor);
    shaders->setUniform("light.position", gLight.position);
    shaders->setUniform("light.intensities", gLight.intensities);
    shaders->setUniform("light.attenuation", gLight.attenuation);
    shaders->setUniform("light.ambientCoefficient", gLight.ambientCoefficient);
    shaders->setUniform("cameraPosition", gCamera.position());
    
    //bind the texture
    glActiveTexture(GL_TEXTURE0); //GL_TEXTURE0
    glBindTexture(GL_TEXTURE_2D, asset->texture->object());
    
    //bind VAO and draw
    glBindVertexArray(asset->vao);
    glDrawArrays(asset->drawType, asset->drawStart, asset->drawCount);
    
    //unbind everything
    glBindVertexArray(0);
    glBindTexture(GL_TEXTURE_2D, 0);
    shaders->stopUsing();
}


// draws a single frame
static void Render() {
    // clear everything
    glClearColor(0.5, 0.9, 1, 1); // background black blue
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    
    // render all the instances
    std::list<ModelInstance>::const_iterator it;
    for(it = gInstances.begin(); it != gInstances.end(); ++it){
        RenderInstance(*it);
    }
    
    // swap the display buffers (displays what was just drawn)
    glfwSwapBuffers(gWindow);
}

// update the scene based on the time elapsed since last update
static void Update(float secondsElapsed) {
    
    //rotate the first instance in `gInstances`
    const GLfloat degreesPerSecond = 100.0f; //180.0f
    gDegreesRotated += secondsElapsed * degreesPerSecond;
    while(gDegreesRotated > 360.0f) gDegreesRotated -= 360.0f;
    
    // donghua
    if(glfwGetKey(gWindow, '3')){
        gInstances.front().transform = glm::translate(glm::mat4(), glm::vec3(-5.0f, (sin((GLfloat)glfwGetTime()*2)- 1.0f) * 0.5f * 3.0f, -17.0f)); // Switched the order
        
        gInstances.back().transform = glm::rotate(gInstances.back().transform, glm::radians(gDegreesRotated), glm::vec3(1,0,0));
    }
    
    if(glfwGetKey(gWindow, '4')){
        gInstances.front().transform = glm::translate(glm::mat4(), glm::vec3((-cos((GLfloat)glfwGetTime())*3.8 + 0.8f) * 0.5f * 3.8f, -cos(glfwGetTime())*cos(glfwGetTime())*7 + 3.4f, -17.0f)); // Switched the order
        
        gInstances.back().transform = glm::rotate(gInstances.back().transform, glm::radians(gDegreesRotated), glm::vec3(0,1,0));
    }
    
    //move position of camera based on WASD keys, and XZ keys for up and down
    const float moveSpeed = 4.0; //units per second
    if(glfwGetKey(gWindow, 'S')){
        gCamera.offsetPosition(secondsElapsed * moveSpeed * -gCamera.forward());
    } else if(glfwGetKey(gWindow, 'W')){
        gCamera.offsetPosition(secondsElapsed * moveSpeed * gCamera.forward());
    }
    if(glfwGetKey(gWindow, 'A')){
        gCamera.offsetPosition(secondsElapsed * moveSpeed * -gCamera.right());
    } else if(glfwGetKey(gWindow, 'D')){
        gCamera.offsetPosition(secondsElapsed * moveSpeed * gCamera.right());
    }
    if(glfwGetKey(gWindow, 'Z')){
        gCamera.offsetPosition(secondsElapsed * moveSpeed * -glm::vec3(0,1,0));
    } else if(glfwGetKey(gWindow, 'X')){
        gCamera.offsetPosition(secondsElapsed * moveSpeed * glm::vec3(0,1,0));
    }
    
    //move light dian->fangxiang
    if(glfwGetKey(gWindow, '1')){
        gLight.ambientCoefficient = 0.45f;
        gLight.position = glm::vec3(-15,0,3);
        //gLight.intensities = glm::vec3(1,0,0); //red
    }
    if(glfwGetKey(gWindow, '2')){
        gLight.ambientCoefficient = 0.70f;
        //gLight.position = gCamera.position();
    }
  
    //texture change
    /*if(glfwGetKey(gWindow, '5')){
        Asphere.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/timg2.png");
        Acone.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/timg2.png");
        Acolumn.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/timg2.png");
    }
    if(glfwGetKey(gWindow, '6')){
        Asphere.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/timg4.png");
        Acone.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/timg4.png");
        Acolumn.texture = LoadTexture("/Users/apple/Documents/graph1/graph1/resources/timg4.png");
    }*/
    /*
    // change light color keyboard
    if(glfwGetKey(gWindow, '6')){
        gLight.intensities = glm::vec3(1,0,0); //red
    }*/
    
    //rotate camera based on mouse movement
    const float mouseSensitivity = 0.1f;
    double mouseX, mouseY;
    glfwGetCursorPos(gWindow, &mouseX, &mouseY);
    gCamera.offsetOrientation(mouseSensitivity * (float)mouseY, mouseSensitivity * (float)mouseX);
    glfwSetCursorPos(gWindow, 0, 0); //reset the mouse, so it doesn't go out of the window
    
    //increase or decrease field of view based on mouse wheel
    const float zoomSensitivity = -0.2f;
    float fieldOfView = gCamera.fieldOfView() + zoomSensitivity * (float)gScrollY;
    if(fieldOfView < 5.0f) fieldOfView = 5.0f;
    if(fieldOfView > 130.0f) fieldOfView = 130.0f;
    gCamera.setFieldOfView(fieldOfView);
    gScrollY = 0;
}

// records how far the y axis has been scrolled
void OnScroll(GLFWwindow* window, double deltaX, double deltaY) {
    gScrollY += deltaY;
}

void OnError(int errorCode, const char* msg) {
    throw std::runtime_error(msg);
}

// the program starts here
void AppMain() {
    // initialise GLFW
    glfwSetErrorCallback(OnError);
    if(!glfwInit())
        throw std::runtime_error("glfwInit failed");
    
    // open a window with GLFW
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
    gWindow = glfwCreateWindow((int)SCREEN_SIZE.x, (int)SCREEN_SIZE.y, "project", NULL, NULL);
    if(!gWindow)
        throw std::runtime_error("glfwCreateWindow failed. Can your hardware handle OpenGL 3.2?");
    
    // GLFW settings
    glfwSetInputMode(gWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    glfwSetCursorPos(gWindow, 0, 0);
    glfwSetScrollCallback(gWindow, OnScroll);
    glfwMakeContextCurrent(gWindow);
    
    // initialise GLEW
    glewExperimental = GL_TRUE; //stops glew crashing on OSX :-/
    if(glewInit() != GLEW_OK)
        throw std::runtime_error("glewInit failed");
    
    // GLEW throws some errors, so discard all the errors so far
    while(glGetError() != GL_NO_ERROR) {}
    
    // print out some info about the graphics drivers
    std::cout << "OpenGL version: " << glGetString(GL_VERSION) << std::endl;
    std::cout << "GLSL version: " << glGetString(GL_SHADING_LANGUAGE_VERSION) << std::endl;
    std::cout << "Vendor: " << glGetString(GL_VENDOR) << std::endl;
    std::cout << "Renderer: " << glGetString(GL_RENDERER) << std::endl;
    
    // make sure OpenGL version 3.2 API is available
    if(!GLEW_VERSION_3_2)
        throw std::runtime_error("OpenGL 3.2 API is not available.");
    
    // OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LESS);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    
    
    // Clear buffers
    glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    
    //=========================
    //Loadskybox();
    //skyInstances();
    
    LoadSphere();
    // create all the instances in the 3D scene based on the Asphere asset
    SphereInstances();
    
    LoadLight();
    lightInstances();
    
    LoadFloor();
    floorInstances();
    LoadLeft();
    leftInstances();
    LoadRight();
    rightInstances();
    LoadTop();
    topInstances();
    LoadFront();
    frontInstances();
    LoadBack();
    backInstances();
    
    // initialise the Asphere asset
    LoadSphere();
    // create all the instances in the 3D scene based on the Asphere asset
    SphereInstances();
    
    // initialise the Acolumn asset
    LoadColumn();
    // create all the instances in the 3D scene based on the Acolumn asset
    ColumnInstances();
    
    // initialise the Acone asset
    LoadCone();
    // create all the instances in the 3D scene based on the Acone asset
    ConeInstances();
    
    LoadWoodenCrateAsset2();
    CreateInstances2();
    
    LoadWoodenCrateAsset3();
    CreateInstances3();
    
    // initialise the Acude asset
    LoadWoodenCrateAsset();
    // create all the instances in the 3D scene based on the Acude asset
    CreateInstances();
    //=========================
 
    // setup gCamera
    gCamera.setPosition(glm::vec3(-8,0,20));
    gCamera.setViewportAspectRatio(SCREEN_SIZE.x / SCREEN_SIZE.y);
    gCamera.setNearAndFarPlanes(0.5f, 100.0f);
    
    // setup gLight
    gLight.position = glm::vec3(-15,0,3); // light position
    gLight.intensities = glm::vec3(1,1,1); // color white
    gLight.attenuation = 0.05f; // level
    gLight.ambientCoefficient = 0.40f;
    
    // run while the window is open
    double lastTime = glfwGetTime();
    while(!glfwWindowShouldClose(gWindow)){
        // process pending events
        glfwPollEvents();
        
        // update the scene based on the time elapsed since last update
        double thisTime = glfwGetTime();
        Update((float)(thisTime - lastTime));
        lastTime = thisTime;
        
        // draw one frame
        Render();
        
        // check for errors
        GLenum error = glGetError();
        if(error != GL_NO_ERROR)
            std::cerr << "OpenGL Error " << error << std::endl;
        
        //exit program if escape key is pressed
        if(glfwGetKey(gWindow, GLFW_KEY_ESCAPE))
            glfwSetWindowShouldClose(gWindow, GL_TRUE);
    }
    
    // clean up and exit
    glfwTerminate();
}


int main(int argc, char *argv[]) {
    try {
        AppMain();
    } catch (const std::exception& e){
        std::cerr << "ERROR: " << e.what() << std::endl;
        return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
}

