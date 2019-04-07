# https://csacademy.com/ieeextreme-practice/task/xplore/
# Score: 100/100 (510 ms - 5884 KB)
import json
lines = int(input())

authors = {}
ordered_authors = []
for i in range(lines):
    jobj = json.loads(input())
    citations = jobj['citing_paper_count']
    author_list = jobj['authors']['authors']
    for entry in author_list:
        author = entry['full_name']
        if author in authors:
            authors[author].append(citations)
        else:
            authors[author] = [citations]

for citations in authors.values():
    citations.sort(reverse=True)

for author in authors.keys():
    mins = []
    citations = authors[author]
    for i in range(len(citations)):
        mins.append(min(i+1,citations[i]))
    ordered_authors.append((author, max(mins)))
ordered_authors.sort(key=lambda x: (-x[1],x[0]))
for author in ordered_authors:
    print('{} {}'.format(author[0],author[1]))
