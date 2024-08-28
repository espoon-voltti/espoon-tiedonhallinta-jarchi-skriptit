
# Program to convert an xml file to json file
# In order to run this, you'll probably have to first run the command: pip install xmltodict
 
import json
import xmltodict
import argparse

# Create an argument parser
parser = argparse.ArgumentParser(description='Convert XML file to JSON file.')
parser.add_argument('input_file', help='Input XML file name')
parser.add_argument('output_file', help='Output JSON file name')

# Parse the arguments
args = parser.parse_args()

# open the input xml file and read data in form of python dictionary using xmltodict module
with open(args.input_file, encoding='utf-8') as xml_file:
     
    data_dict = xmltodict.parse(xml_file.read())
    # xml_file.close()
     
    # generate the object using json.dumps() corresponding to json data
     
    json_data = json.dumps(data_dict, indent=2, ensure_ascii=False)
     
    # Write the json data to output 
    # json file
    with open(args.output_file, "w", encoding='utf-8') as json_file:
        json_file.write(json_data)
        # json_file.close()
