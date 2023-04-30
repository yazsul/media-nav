import sys
import json

input_dir = sys.argv[1]
output_dir = sys.argv[2]

file = open(input_dir + '\\article.json')
print(json.load(file)['article'])
file.close()

dictionary = {
    'output': 'output Example',
    'x': 5,
    'y': 10,
    'chartType': 'chart type example'
}
json_object = json.dumps(dictionary, indent=4)
with open(output_dir + '\\someOutput.json', 'w') as outfile:
    outfile.write(json_object)

print('Skeleton is implemented!')
print('This should be the entry point of your scripts')
